<template>
  <div class="rent-package-wrapper">
    <h1>Rent movies</h1>
    <div id="rent-package-buttons">
      <button @click="submitRentalPackage()" id="rent-package-submit-button">Submit</button>
      <button @click="addRentalElement()" id="rent-package-add-button">Add record</button>
    </div>
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
        <button @click="deleteRecord(scope.row)">Delete</button>
      </template>
    </vue-virtual-table>

    <form-modal name="selectOfficeModal" :width="600" :height="500">
      <div class="modal-box select-essentials-modal">
        <span class="modal-box-title">Specify rental office:</span>
        Name:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalEssentials.idRentalOffice"
          :items="selectSuggestionsData.rentalOffices"
          item-text="rentalOfficeNameID"
          item-value="idRentalOffice"/><br/>
        <button @click="$modal.hide('selectOfficeModal')">Cancel</button>
        <button @click="goToSelectEmployeeClientModal()" style="float:right;">Next</button>
      </div>
    </form-modal>

    <form-modal name="selectEmployeeClientModal" :width="600" :height="570">
      <div class="modal-box select-essentials-modal">
        <span class="modal-box-title">Specify employee and client:</span>
        Employee:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalEssentials.idEmployee"
          :items="selectSuggestionsData.employees"
          item-text="employeeNameID"
          item-value="idEmployee"/><br/>
        Client:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalEssentials.idClient"
          :items="selectSuggestionsData.clients"
          item-text="clientNameAddress"
          item-value="idClient"/><br/>
        <button @click="$modal.hide('selectEmployeeClientModal')">Cancel</button>
        <button @click="goToAddRecordModal()" style="float:right;">Next</button>
      </div>
    </form-modal>

    <form-modal name="selectRentalElementModal" :width="650" :height="500">
      <div class="modal-box select-essentials-modal">
        <span class="modal-box-title">Specify rental element:</span>
        Movie:<br/>
        <cool-select
          class="cool-select-input"
          v-model="RentalElementObject.idMovieWarehouse"
          :items="selectSuggestionsData.warehouseMovies"
          item-text="titleDirectorQuantity"
          item-value="idMovieWarehouse"/><br/>
        Amount:<br/>
        <input type="number" v-model="RentalElementObject.amountOfRentals"><br/>
        <button @click="finishAddingRentalElementToTable()">Finish</button>
        <button @click="addRentalElementToTable()" style="float:right;">Add</button>
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
// TODO edit, delete
// TODO dekrementacja quantity w warehouse
export default {
  name: 'RentPackage',
  components: {
    VueVirtualTable,
    CoolSelect
  },
  data () {
    return {
      tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
        { prop: 'title', name: 'Movie', searchable: true, sortable: true },
        { prop: 'director', name: 'Director', width: 170, searchable: true, sortable: true },
        { prop: 'amountOfRentals', name: 'Amount', width: 80, numberFilter: true, sortable: true },
        { prop: 'clientName', name: 'Client', width: 200 },
        { prop: 'employeeName', name: 'Employee', width: 200 },
        { prop: 'rentalOfficeName', name: 'Office', width: 130 },
        { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
      ],
      tableData: [],
      RentalElementObject: {
        amountOfRentals: 1,
        idMovieWarehouse: '',
        idRental: ''
      },
      postObject: {
        rental: {
          rentalDate: '',
          returnDate: ''
        }
      },
      RentalEssentials: {
        idRentalOffice: '',
        idEmployee: '',
        idClient: '',
        rentalOfficeName: '',
        employeeName: '',
        clientName: ''
      },
      selectSuggestionsData: {
        rentalOffices: [],
        warehouseMovies: [],
        clients: [],
        employees: []
      },
      oldRecordData: {},
      newRecordData: {},
      cleanPostObject: {},
      cleanRentalElementObject: {},
      alertText: '',
      RentalElementPostErrorFlag: false,
      isOfficeSpecified: false,
      isEmployeeClientSpecified: false,
      isSelectSuggestionsLoaded: false
    }
  },
  created () {
    this.cleanRentalElementObject = _.cloneDeep(this.RentalElementObject)
    this.cleanPostObject = _.cloneDeep(this.postObject)
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
  },
  methods: {
    addRentalElement () {
      if (!this.isOfficeSpecified) {
        this.$modal.show('selectOfficeModal')
      } else if (!this.isEmployeeClientSpecified) {
        this.$modal.show('selectEmployeeClientModal')
      } else {
        this.refreshMovieWarehouse()
        this.$modal.show('selectRentalElementModal')
      }
    },

    goToSelectEmployeeClientModal () {
      if (this.RentalEssentials.idRentalOffice != null) {
        if (!this.isSelectSuggestionsLoaded) {
          axios.get('/rentalOffice/clients')
            .then(response => {
              function clientSuggestionConstructor (idClient, clientFirstName, clientLastName, street, houseNumber) {
                this.idClient = idClient
                this.clientNameAddress = clientFirstName + ' ' + clientLastName + ', ' + street + ' ' + houseNumber
                this.clientName = clientFirstName + ' ' + clientLastName
              }
              let i
              for (i = 0; i < response.data.length; i++) {
                this.selectSuggestionsData.clients.push(new clientSuggestionConstructor(response.data[i].idClient, response.data[i].clientFirstName, response.data[i].clientLastName,
                  response.data[i].adress.street, response.data[i].adress.houseNumber))
              }
            })
          axios.get('/rentalOffice/' + this.RentalEssentials.idRentalOffice + '/employees')
            .then(response => {
              function employeeSuggestionConstructor (idEmployee, firstName, lastName) {
                this.idEmployee = idEmployee
                this.employeeNameID = firstName + ' ' + lastName + ' ID: ' + idEmployee
                this.employeeName = firstName + ' ' + lastName
              }
              let i
              for (i = 0; i < response.data.length; i++) {
                this.selectSuggestionsData.employees.push(new employeeSuggestionConstructor(response.data[i].idEmployee, response.data[i].firstName, response.data[i].lastName))
              }
            })
          let specifiedOfficeIndex = this.selectSuggestionsData.rentalOffices.findIndex(obj =>
            obj.idRentalOffice == this.RentalEssentials.idRentalOffice)
          this.RentalEssentials.rentalOfficeName = this.selectSuggestionsData.rentalOffices[specifiedOfficeIndex].rentalOfficeName
          this.isOfficeSpecified = true
          this.isSelectSuggestionsLoaded = true
        }
        this.$modal.show('selectEmployeeClientModal')
        this.$modal.hide('selectOfficeModal')
      } else {
        this.$modal.show('alertModal', { text: 'Please specify your rental office before continuing.' })
      }
    },

    goToAddRecordModal () {
      if (this.RentalEssentials.idClient != null && this.RentalEssentials.idEmployee != null) {
        this.refreshMovieWarehouse()
        this.isEmployeeClientSpecified = true
        this.$modal.show('selectRentalElementModal')
        this.$modal.hide('selectEmployeeClientModal')
        let specifiedEmployeeIndex = this.selectSuggestionsData.employees.findIndex(obj =>
          obj.idEmployee == this.RentalEssentials.idEmployee)
        this.RentalEssentials.employeeName = this.selectSuggestionsData.employees[specifiedEmployeeIndex].employeeName
        let specifiedClientIndex = this.selectSuggestionsData.clients.findIndex(obj =>
          obj.idClient == this.RentalEssentials.idClient)
        this.RentalEssentials.clientName = this.selectSuggestionsData.clients[specifiedClientIndex].clientName
      } else {
        this.$modal.show('alertModal', { text: 'Please specify yourself and client before continuing.' })
      }
    },

    addRentalElementToTable () {
      function tableDataConstructor (idMovieWarehouse, title, director, amountOfRentals, clientName, employeeName, rentalOfficeName) {
        this.idMovieWarehouse = idMovieWarehouse
        this.title = title
        this.director = director
        this.amountOfRentals = amountOfRentals
        this.clientName = clientName
        this.employeeName = employeeName
        this.rentalOfficeName = rentalOfficeName
      }
      if (this.RentalElementObject.amountOfRentals > 0 && this.RentalElementObject.idMovieWarehouse != null) {
        let addedMovieIndex = this.selectSuggestionsData.warehouseMovies.findIndex(obj =>
          obj.idMovieWarehouse == this.RentalElementObject.idMovieWarehouse)
        let specifiedMovieQuantity = this.selectSuggestionsData.warehouseMovies[addedMovieIndex].quantity
        if (specifiedMovieQuantity >= this.RentalElementObject.amountOfRentals) {
          let specifiedMovieTitle = this.selectSuggestionsData.warehouseMovies[addedMovieIndex].title
          let specifiedMovieDirector = this.selectSuggestionsData.warehouseMovies[addedMovieIndex].director
          this.tableData.push(new tableDataConstructor(this.RentalElementObject.idMovieWarehouse, specifiedMovieTitle, specifiedMovieDirector,
            this.RentalElementObject.amountOfRentals, this.RentalEssentials.clientName, this.RentalEssentials.employeeName,
            this.RentalEssentials.rentalOfficeName))
          this.RentalElementObject = _.cloneDeep(this.cleanRentalElementObject)
          this.refreshMovieWarehouse()
        } else {
          this.$modal.show('alertModal', { text: 'Specified amount to rent is greater than quantity in warehouse.' })
        }
      } else {
        this.$modal.show('alertModal', { text: 'Please specify movie to rent and correct amount.' })
      }
    },

    refreshMovieWarehouse () {
      this.selectSuggestionsData.warehouseMovies = []
      axios.get('/rentalOffice/' + this.RentalEssentials.idRentalOffice + '/warehouse/all')
        .then(response => {
          function warehouseSuggestionConstructor (idMovieWarehouse, title, director, quantity) {
            this.idMovieWarehouse = idMovieWarehouse
            this.titleDirectorQuantity = title + ', by ' + director + ', Quantity: ' + quantity
            this.title = title
            this.director = director
            this.quantity = quantity
          }
          let i
          for (i = 0; i < response.data.length; i++) {
            if (response.data[i].quantity > 0 &&
                  this.tableData.findIndex(tableElem => (tableElem.idMovieWarehouse.toString() == response.data[i].idMovieWarehouse)) == -1) {
              this.selectSuggestionsData.warehouseMovies.push(new warehouseSuggestionConstructor(response.data[i].idMovieWarehouse, response.data[i].movie.title,
                response.data[i].movie.director, response.data[i].quantity))
            }
          }
        })
    },

    finishAddingRentalElementToTable () {
      if (this.RentalElementObject.amountOfRentals == 1 && this.RentalElementObject.idMovieWarehouse == null) {
        this.$modal.hide('selectRentalElementModal')
      } else {
        this.addRentalElementToTable()
        this.$modal.hide('selectRentalElementModal')
      }
    },

    submitRentalPackage () {
      this.postObject.rental.rentalDate = new Date(Date.now()).toJSON()
      axios.post('/rental/client/' + this.RentalEssentials.idClient + '/employee/' + this.RentalEssentials.idEmployee, this.postObject.rental)
        .then(response => {
          function rentalElementConstructor (amountOfRentals, idMovieWarehouse, idRental) {
            this.amountOfRentals = amountOfRentals
            this.idMovieWarehouse = idMovieWarehouse
            this.idRental = idRental
          }
          let i
          for (i = 0; i < this.tableData.length; i++) {
            let rentalElementPostObject = new rentalElementConstructor(this.tableData[i].amountOfRentals, this.tableData[i].idMovieWarehouse,
              response.data)
            axios.post('/rentalElement/' + rentalElementPostObject.idMovieWarehouse + '/' + rentalElementPostObject.idRental + '/', rentalElementPostObject)
              .catch(error => {
                this.RentalElementPostErrorFlag = true
              })
            if (this.RentalElementPostErrorFlag) break
          }
          this.$modal.show('alertModal', { text: 'Operation succeeded.' })
        })
        .catch(error => {
          if (error.response) {
            this.$modal.show('alertModal',
              { text: 'Operation failed.  |  ' + error.response.status + '  |  ' + error.response.data.error + '  |  ' + error.response.data.message })
          }
        })
        .then(() => {
          if (this.RentalElementPostErrorFlag) {
            this.$modal.show('alertModal', { text: 'Operation failed. Please delete created records in Rental elements, then in Rentals.' })
          }
          this.postObject = _.cloneDeep(this.cleanPostObject)
          this.tableData = []
          this.RentalEssentials.idClient = ''
          this.RentalEssentials.clientName = ''
          this.isEmployeeClientSpecified = false
          this.RentalElementPostErrorFlag = false
        })
    },

    deleteRecord: function (recordData) {
      let deleteElemIndex = this.tableData.findIndex(tableElem => (tableElem.idMovieWarehouse == recordData.idMovieWarehouse))
      this.tableData.splice(deleteElemIndex, 1)
    },

    beforeOpenAlert (event) {
      this.alertText = event.params.text
    }
  }
}
</script>
<style scoped>

</style>
