<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Employee"></TheDatabaseNavigation>
    <h1>Employees</h1>
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
        ID Employee:<br/>
        <input type="text" v-model="newRecordData.idEmployee" disabled><br/>
        First name:<br/>
        <input type="text" v-model="newRecordData.firstName"><br/>
        Last name:<br/>
        <input type="text" v-model="newRecordData.lastName"><br/>
        Position:<br/>
        <input type="text" v-model="newRecordData.position"><br/>
        E-mail:<br/>
        <input type="text" v-model="newRecordData.email"><br/>
        Phone number:<br/>
        <input type="text" v-model="newRecordData.phone"><br/>
        ID Rental office:<br/>
        <input type="text" v-model="newRecordData.idRentalOffice" disabled><br/>
        Rental office name:<br/>
        <input type="text" v-model="newRecordData.rentalOfficeName" disabled><br/>
        ID Address:<br/>
        <input type="text" v-model="newRecordData.idAdress" disabled><br/>
        Address:<br/>
        <input type="text" v-model="newRecordData.adressComplete" disabled><br/>
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
    name: 'Employee',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'idEmployee', name: 'ID', width: 28, sortable: true, numberFilter: true },
          { prop: 'firstName', name: 'First name', width: 90, searchable: true, sortable: true },
          { prop: 'lastName', name: 'Last name', width: 90, searchable: true, sortable: true },
          { prop: 'position', name: 'Position', width: 100, filterable: true, sortable: true },
          { prop: 'email', name: 'E-mail', width: 230, searchable: true },
          { prop: 'phone', name: 'Phone', width: 84, searchable: true },
          { prop: 'idRentalOffice', name: 'ID', width: 26, sortable: true, numberFilter: true },
          { prop: 'rentalOfficeName', name: 'Office name', width: 126, filterable: true, sortable: true },
          { prop: 'idAdress', name: 'ID', width: 28, sortable: true, numberFilter: true },
          { prop: 'adressComplete', name: 'Address', searchable: true, sortable: true },
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        oldRecordData: {},
        newRecordData: {},
        alertText: ''
      }
    },
    created () {
      axios.get('/rentalOffice/employees')
        .then(response => {
          function responseConstructor(idEmployee, firstName, lastName, position, email,
                                       phone, idRentalOffice, rentalOfficeName, idAdress, city, street, houseNumber){
            this.idEmployee = idEmployee
            this.firstName = firstName
            this.lastName = lastName
            this.position = position
            this.email = email
            this.phone = phone
            this.idRentalOffice = idRentalOffice
            this.rentalOfficeName = rentalOfficeName
            this.idAdress = idAdress
            this.adressComplete = city + ', ' + street + ' ' + houseNumber
          }
          let i
          for(i=0; i<response.data.length; i++) {
            this.tableData.push(new responseConstructor(response.data[i].idEmployee, response.data[i].firstName, response.data[i].lastName,
              response.data[i].position, response.data[i].email,  response.data[i].phone, response.data[i].rentalOffice.idRentalOffice,
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
        axios.put('/rentalOffice/' + this.oldRecordData.idRentalOffice + '/employees/' + this.oldRecordData.idEmployee +
                  '/' + this.oldRecordData.idAdress, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idEmployee == this.oldRecordData.idEmployee)
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
        axios.delete('/rentalOffice/employees/' + recordData.idEmployee)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idEmployee == recordData.idEmployee)
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
