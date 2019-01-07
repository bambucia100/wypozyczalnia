<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Client"></TheDatabaseNavigation>
    <h1>Clients</h1>
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
        <button @click="editRecord(scope.row)">Edit</button>
        <button @click="deleteRecord(scope.row)">Delete</button>
      </template>
    </vue-virtual-table>
    <form-modal name="editModal" :width="600" :height="650">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID Client:<br/>
        <input type="text" v-model="newRecordData.idClient" disabled><br/>
        First name:<br/>
        <input type="text" v-model="newRecordData.clientFirstName"><br/>
        Last name:<br/>
        <input type="text" v-model="newRecordData.clientLastName"><br/>
        Registration date:<br/>
        <input type="text" v-model="newRecordData.registrationDate"><br/>
        E-mail:<br/>
        <input type="text" v-model="newRecordData.email"><br/>
        Phone number:<br/>
        <input type="text" v-model="newRecordData.number"><br/>
        ID Rental office:<br/>
        <input type="text" v-model="newRecordData.idRentalOffice" disabled><br/>
        Rental office name:<br/>
        <input type="text" v-model="newRecordData.rentalOfficeName" disabled><br/>
        ID Address:<br/>
        <input type="text" v-model="newRecordData.idAdress" disabled><br/>
        Address:<br/>
        <input type="text" v-model="newRecordData.adress" disabled><br/>
        <button @click="editRecordSubmit" style="float:right;">Submit</button><button @click="$modal.hide('editModal')" >Cancel</button>
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
  import TheDatabaseNavigation from '../components/TheDatabaseNavigation.vue'
  import VueVirtualTable from 'vue-virtual-table'

  export default {
    name: 'Client',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'idClient', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'clientFirstName', name: 'First name', width: 90, searchable: true, sortable: true },
          { prop: 'clientLastName', name: 'Last name', width: 90, searchable: true, sortable: true },
          { prop: 'registrationDate', name: 'Reg. date', width: 90, searchable: true, sortable: true },
          { prop: 'email', name: 'E-mail', width: 200, searchable: true },
          { prop: 'number', name: 'Phone', width: 84, searchable: true },
          { prop: 'idRentalOffice', name: 'ID', width: 26, sortable: true, numberFilter: true },
          { prop: 'rentalOfficeName', name: 'Office name', width: 150, filterable: true, sortable: true },
          { prop: 'idAdress', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'adress', name: 'Address', searchable: true, sortable: true, width: 150 },
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        oldRecordData: {},
        newRecordData: {},
        alertText: ''
      }
    },
    created () {
      axios.get('/rentalOffice/clients/all')
        .then(response => {
          function responseConstructor(idClient, clientFirstName, clientLastName, registrationDate, email,
                                       number, idRentalOffice, rentalOfficeName, idAdress, city, street, houseNumber){
            this.idClient = idClient
            this.clientFirstName = clientFirstName
            this.clientLastName = clientLastName
            this.registrationDate = registrationDate
            this.email = email
            this.number = number
            this.idRentalOffice = idRentalOffice
            this.rentalOfficeName = rentalOfficeName
            this.idAdress = idAdress
            this.adress = city + ', ' + street + ' ' + houseNumber
          }
          let i
          for(i=0; i<response.data.length; i++) {
            this.tableData.push(new responseConstructor(response.data[i].idClient, response.data[i].clientFirstName, response.data[i].clientLastName,
              response.data[i].registrationDate, response.data[i].email,  response.data[i].number, response.data[i].rentalOffice.idRentalOffice,
              response.data[i].rentalOffice.rentalOfficeName, response.data[i].adress.idAdress, response.data[i].adress.city,
              response.data[i].adress.street, response.data[i].adress.houseNumber))
          }
        })
    },
    methods: {
      editRecord: function (recordData) {
        this.$modal.show('editModal')
        this.oldRecordData = recordData
        this.newRecordData = recordData
      },
      editRecordSubmit: function () {
        axios.put('/rentalOffice/' + this.oldRecordData.idRentalOffice + '/clients/' + this.oldRecordData.idClient, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idClient == this.oldRecordData.idClient)
            this.$set(this.tableData, editElemIndex, this.newRecordData)
          })
          .catch(error => {
            if (error.response) {
              this.$modal.show('alertModal',
                { text: 'Operation failed.  |  ' + error.response.status + '  |  ' + error.response.data.error + '  |  ' + error.response.data.message })
            }
          })
      },
      deleteRecord: function (recordData) {
        axios.delete('/rentalOffice/' + recordData.idRentalOffice + '/clients/' + recordData.idClient)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idClient == recordData.idClient)
            this.tableData.splice(deleteElemIndex, 1)
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
