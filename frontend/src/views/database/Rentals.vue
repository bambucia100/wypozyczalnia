<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Rentals"></TheDatabaseNavigation>
    <h1>Rentals</h1>
    <vue-virtual-table
      class="v-table"
      :config="tableConfig"
      :data="tableData"
      :minWidth="800"
      :height=$tableHeight
      :itemHeight="34"
      :bordered="true"
    >
      <template slot-scope="scope" slot="actionCommon">
        <button @click="editRecord(scope.row)">Edit</button>
        <button @click="deleteRecord(scope.row)">Delete</button>
      </template>
    </vue-virtual-table>
    <form-modal name="editModal" :width="600" :height="500">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID Rental:<br/>
        <input type="text" v-model="newRecordData.idRental" disabled><br/>
        ID Client:<br/>
        <input type="text" v-model="newRecordData.idClient" disabled><br/>
        Client name:<br/>
        <input type="text" v-model="newRecordData.clientName" disabled><br/>
        ID Employee:<br/>
        <input type="text" v-model="newRecordData.idEmployee" disabled><br/>
        Employee name:<br/>
        <input type="text" v-model="newRecordData.employeeName" disabled><br/>
        Rental date:<br/>
        <input type="date" v-model="newRecordData.rentalDate"><br/>
        Return date:<br/>
        <input type="date" v-model="newRecordData.returnDate" :disabled="isReturnDateNull"><br/>
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
  import TheDatabaseNavigation from '../../components/TheDatabaseNavigation.vue'
  import VueVirtualTable from 'vue-virtual-table'

  export default {
    name: 'Rentals',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'idRental', name: 'ID', width: 46, sortable: true, numberFilter: true },
          { prop: 'idClient', name: 'ID Client', width: 80, numberFilter: true, sortable: true },
          { prop: 'clientName', name: 'Client', searchable: true, sortable: true },
          { prop: 'idEmployee', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'employeeName', name: 'Employee', searchable: true, sortable: true },
          { prop: 'rentalDate', name: 'Rental', width: 100, searchable: true },
          { prop: 'returnDate', name: 'Return', width: 100, searchable: true },
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        oldRecordData: {},
        newRecordData: {},
        alertText: '',
        isReturnDateNull: true
      }
    },
    created () {
      axios.get('/rentals/all')
        .then(response => {
          function responseConstructor(idRental, idClient, clientFirstName, clientLastName, idEmployee, employeeFirstName,
                                       employeeLastName, rentalDate, returnDate){
            this.idRental = idRental
            this.idClient = idClient
            this.clientName = clientFirstName + ' ' + clientLastName
            this.idEmployee = idEmployee
            this.employeeName = employeeFirstName + ' ' + employeeLastName
            this.rentalDate = rentalDate
            this.returnDate = returnDate
          }
          let i
          for(i=0; i<response.data.length; i++) {
            this.tableData.push(new responseConstructor(response.data[i].idRental, response.data[i].client.idClient, response.data[i].client.clientFirstName,
              response.data[i].client.clientLastName, response.data[i].employee.idEmployee, response.data[i].employee.firstName,
              response.data[i].employee.lastName, response.data[i].rentalDate, response.data[i].returnDate))
          }
        })
    },
    methods: {
      editRecord: function (recordData) {
        if(recordData.returnDate == null) { this.isReturnDateNull = true }
        else { this.isReturnDateNull = false }
        this.$modal.show('editModal')
        this.oldRecordData = recordData
        this.newRecordData = recordData
      },
      editRecordSubmit: function () {
        axios.put('/rental/' + this.oldRecordData.idRental + '/client/' + this.oldRecordData.idClient +
          '/employee/' + this.oldRecordData.idEmployee, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idRental == this.oldRecordData.idRental)
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
        axios.delete('/rental/' + recordData.idRental)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idRental == recordData.idRental)
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
