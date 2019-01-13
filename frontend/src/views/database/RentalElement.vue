<template>
  <div class="db-data">
    <TheDatabaseNavigation link="RentalElement"></TheDatabaseNavigation>
    <h1>Rental elements</h1>
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
    <form-modal name="editModal" :width="600" :height="610">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID Rental:<br/>
        <input type="text" v-model="newRecordData.idRental" disabled><br/>
        ID Movie in warehouse:<br/>
        <input type="text" v-model="newRecordData.idMovieWarehouse" disabled><br/>
        Amount taken:<br/>
        <input type="number" v-model="newRecordData.amountOfRentals" required><br/>
        ID Client:<br/>
        <input type="text" v-model="newRecordData.idClient" disabled><br/>
        Client name:<br/>
        <input type="text" v-model="newRecordData.clientName" disabled><br/>
        ID Rental office:<br/>
        <input type="text" v-model="newRecordData.idRentalOffice" disabled><br/>
        Rental office name:<br/>
        <input type="text" v-model="newRecordData.rentalOfficeName" disabled><br/>
        ID Movie:<br/>
        <input type="text" v-model="newRecordData.idMovie" disabled><br/>
        Movie title:<br/>
        <input type="text" v-model="newRecordData.title" disabled><br/>
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
    name: 'RentalElement',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'idRental', name: 'ID Rental', width: 80, sortable: true, numberFilter: true },
          { prop: 'idClient', name: 'ID', width: 36, numberFilter: true, sortable: true },
          { prop: 'clientName', name: 'Client', width: 200, searchable: true, sortable: true },
          { prop: 'idMovieWarehouse', name: 'ID Warehouse', width: 120, numberFilter: true, sortable: true },
          { prop: 'idRentalOffice', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'rentalOfficeName', name: 'Office', width: 130, filterable: true, sortable: true },
          { prop: 'amountOfRentals', name: 'Amount', width: 80, numberFilter: true, sortable: true },
          { prop: 'idMovie', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'title', name: 'Movie', searchable: true, sortable: true },
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        oldRecordData: {},
        newRecordData: {},
        alertText: ''
      }
    },
    created () {
      axios.get('/rentalElement/all')
        .then(response => { // idRental/idMovieWarehouse/idClient/clientName/idRentalOffice/rentalOfficeName/idMovie/title/amount_of_rentals
          function responseConstructor(idRental, idMovieWarehouse, idClient, clientFirstName, clientLastName, idRentalOffice, rentalOfficeName,
                                       idMovie, title, amountOfRentals){
            this.idRental = idRental
            this.idMovieWarehouse = idMovieWarehouse
            this.idClient = idClient
            this.clientName = clientFirstName + ' ' + clientLastName
            this.idRentalOffice = idRentalOffice
            this.rentalOfficeName = rentalOfficeName
            this.idMovie = idMovie
            this.title = title
            this.amountOfRentals = amountOfRentals
          }
          let i
          for(i=0; i<response.data.length; i++) {
            this.tableData.push(new responseConstructor(response.data[i].rental.idRental, response.data[i].moviesWarehouse.idMovieWarehouse,
              response.data[i].rental.client.idClient, response.data[i].rental.client.clientFirstName, response.data[i].rental.client.clientLastName,
              response.data[i].moviesWarehouse.rentalOffice.idRentalOffice, response.data[i].moviesWarehouse.rentalOffice.rentalOfficeName,
              response.data[i].moviesWarehouse.movie.idMovie, response.data[i].moviesWarehouse.movie.title, response.data[i].amountOfRentals))
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
        axios.put('/rentalElement/' + this.oldRecordData.idMovieWarehouse + '/' + this.oldRecordData.idRental, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem =>
              (tableElem.idRental == this.oldRecordData.idRental && tableElem.idMovieWarehouse == this.oldRecordData.idMovieWarehouse))
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
        axios.delete('/rentalElement/' + recordData.idMovieWarehouse + '/' + recordData.idRental)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableElem =>
              (tableElem.idMovieWarehouse == recordData.idMovieWarehouse && tableElem.idRental == recordData.idRental))
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
