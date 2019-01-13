<template>
  <div class="rent-package-wrapper">
    <h1>Return movies package</h1>
    <vue-virtual-table
      class="v-table"
      :config="tableConfig"
      :data="tableData"
      :minWidth="800"
      :height=$tableHeight
      :itemHeight="45"
      :bordered="true"
    >
      <template slot-scope="scope" slot="actionCommon">
        <button @click="returnMovie(scope.row)">Return</button>
      </template>
    </vue-virtual-table>

    <form-modal name="selectOfficeModal" :width="600" :height="500">
      <div class="modal-box select-essentials-modal">
        <span class="modal-box-title">Specify rental office:</span>
        Name:<br/>
        <cool-select
          class="cool-select-input"
          v-model="specifiedRentalOffice.idRentalOffice"
          :items="selectSuggestionsData.rentalOffices"
          item-text="rentalOfficeNameID"
          item-value="idRentalOffice"/><br/>
        <button @click="$modal.hide('selectOfficeModal')">Cancel</button>
        <button @click="getRentalsInRentalOffice()" style="float:right;">Next</button>
      </div>
    </form-modal>

    <form-modal name="alertModal" @before-open="beforeOpenAlert" :height="$alertHeight" :width="$alertWidth">
      <div class="modal-box alert-modal-box">
        {{alertText}}
        <button @click="$modal.hide('alertModal')">Close</button>
      </div>
    </form-modal>
  </div>
</template>

<script>
import axios from 'axios'
import _ from 'lodash'
import VueVirtualTable from 'vue-virtual-table'
import { CoolSelect } from 'vue-cool-select'
export default {
  name: 'ReturnPackage',
  components: {
    VueVirtualTable,
    CoolSelect
  },
  data () {
    return {
      tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
        { prop: 'idRental', name: 'ID', width: 46, sortable: true, numberFilter: true },
        { prop: 'clientName', name: 'Client', width: 200, searchable: true, sortable: true },
        { prop: 'adressComplete', name: 'Address', width: 200, searchable: true, sortable: true },
        { prop: 'rentalDate', name: 'Rental', width: 100, searchable: true, sortable: true },
        { prop: 'titles', name: 'Rented movies', searchable: true },
        { prop: 'rentalOfficeName', name: 'Office', width: 125 },
        { prop: 'employeeName', name: 'Employee', width: 200, filterable: true, sortable: true },
        { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 80 }
      ],
      tableData: [],
      titlesForRentals: [],
      returnObject: {
        idRental: '',
        returnDate: ''
      },
      specifiedRentalOffice: {
        idRentalOffice: '',
        rentalOfficeName: ''
      },
      selectSuggestionsData: {
        rentalOffices: []
      },
      alertText: ''
    }
  },
  created () {
    axios.get('/rentalOffice/all')
      .then(response => {
        function responseConstructor (idRentalOffice, rentalOfficeName) {
          this.idRentalOffice = idRentalOffice
          this.rentalOfficeNameID = rentalOfficeName + ' ID: ' + idRentalOffice
          this.rentalOfficeName = rentalOfficeName
        }
        let i
        for (i = 0; i < response.data.length; i++) {
          this.selectSuggestionsData.rentalOffices.push(new responseConstructor(response.data[i].idRentalOffice, response.data[i].rentalOfficeName))
        }
      })
      .then(() => {
        this.$modal.show('selectOfficeModal')
      })
  },
  methods: {
    getRentalsInRentalOffice () {
      if (this.specifiedRentalOffice.idRentalOffice != null) {
        let specifiedOfficeIndex = this.selectSuggestionsData.rentalOffices.findIndex(obj =>
          obj.idRentalOffice == this.specifiedRentalOffice.idRentalOffice)
        this.specifiedRentalOffice.rentalOfficeName = this.selectSuggestionsData.rentalOffices[specifiedOfficeIndex].rentalOfficeName
        axios.get('rentals/' + this.specifiedRentalOffice.idRentalOffice + '/notReturned')
          .then(response => {
            function responseConstructor (idRental, clientFirstName, clientLastName, street, houseNumber,
              rentalDate, titles, rentalOfficeName, employeeFirstName, employeeLastName) {
              this.idRental = idRental
              this.clientName = clientFirstName + ' ' + clientLastName
              this.adressComplete = street + ' ' + houseNumber
              this.rentalDate = rentalDate
              this.rentalOfficeName = rentalOfficeName
              this.employeeName = employeeFirstName + ' ' + employeeLastName
              let tempTitles = ''
              let i
              for (i = 0; i < titles.length; i++) {
                tempTitles += titles[i] + ', '
              }
              this.titles = tempTitles.slice(0, tempTitles.length - 2)
            }
            let i
            for (i = 0; i < response.data.length; i++) {
              this.tableData.push(new responseConstructor(response.data[i][0].idRental, response.data[i][0].client.clientFirstName,
                response.data[i][0].client.clientLastName, response.data[i][0].client.adress.street, response.data[i][0].client.adress.houseNumber,
                response.data[i][0].rentalDate, response.data[i][1], response.data[i][0].employee.rentalOffice.rentalOfficeName,
                response.data[i][0].employee.firstName, response.data[i][0].employee.lastName))
            }
          })
        this.$modal.hide('selectOfficeModal')
      } else {
        this.$modal.show('alertModal', { text: 'Please specify your rental office before continuing.' })
      }
    },

    returnMovie: function (recordData) {
      axios.put('/rental/' + recordData.idRental + '/return', { idRental: recordData.idRental, returnDate: new Date(Date.now()).toJSON() })
        .then(response => {
          this.$modal.show('alertModal', { text: 'Movies returned. Fee: ' + response.data })
          let returnElemIndex = this.tableData.findIndex(tableElem => (tableElem.idRental == recordData.idRental))
          this.tableData.splice(returnElemIndex, 1)
        })
        .catch(error => {
          if (error.response) {
            this.$modal.show('alertModal',
              { text: 'Operation failed.  |  ' + error.response.status + '  |  ' + error.response.data.error + '  |  ' + error.response.data.message })
          }
        })
    },

    beforeOpenAlert (event) {
      this.alertText = event.params.text
    }
  }
}
</script>
<style scoped>

</style>
