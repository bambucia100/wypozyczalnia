<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Warehouse"></TheDatabaseNavigation>
    <h1>Movies warehouse</h1>
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
    <form-modal name="editModal" :width="600" :height="500">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID Movie in warehouse:<br/>
        <input type="text" v-model="newRecordData.idMovieWarehouse" disabled><br/>
        ID Movie:<br/>
        <input type="text" v-model="newRecordData.idMovie" disabled><br/>
        Movie title:<br/>
        <input type="text" v-model="newRecordData.title" disabled><br/>
        Quantity in warehouse:<br/>
        <input type="text" v-model="newRecordData.quantity"><br/>
        Daily charge:<br/>
        <input type="text" v-model="newRecordData.charge"><br/>
        ID Rental office:<br/>
        <input type="text" v-model="newRecordData.idRentalOffice" disabled><br/>
        Rental office name:<br/>
        <input type="text" v-model="newRecordData.rentalOfficeName" disabled><br/>
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
    name: 'MoviesWarehouse',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'idMovieWarehouse', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'idMovie', name: 'ID Movie', width: 90, numberFilter: true, sortable: true },
          { prop: 'title', name: 'Title', searchable: true, sortable: true },
          { prop: 'quantity', name: 'Quantity', width: 90, numberFilter: true, sortable: true },
          { prop: 'charge', name: 'Charge', width: 90, numberFilter: true, sortable: true },
          { prop: 'idRentalOffice', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'rentalOfficeName', name: 'Office name', width: 150, filterable: true, sortable: true },
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        oldRecordData: {},
        newRecordData: {},
        alertText: ''
      }
    },
    created () {
      axios.get('/rentalOffice/warehouse/all')
        .then(response => {
          function responseConstructor(idMovieWarehouse, idMovie, title, quantity, charge, idRentalOffice, rentalOfficeName){
            this.idMovieWarehouse = idMovieWarehouse
            this.idMovie = idMovie
            this.title = title
            this.quantity = quantity
            this.charge = charge
            this.idRentalOffice = idRentalOffice
            this.rentalOfficeName = rentalOfficeName
          }
          let i
          for(i=0; i<response.data.length; i++) {
            this.tableData.push(new responseConstructor(response.data[i].idMovieWarehouse, response.data[i].movie.idMovie,
              response.data[i].movie.title, response.data[i].quantity, response.data[i].charge,
              response.data[i].rentalOffice.idRentalOffice, response.data[i].rentalOffice.rentalOfficeName,))
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
        axios.put('/rentalOffice/' + this.oldRecordData.idRentalOffice + '/warehouse/' + this.oldRecordData.idMovie +
          '/' + this.oldRecordData.idMovieWarehouse, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idMovieWarehouse == this.oldRecordData.idMovieWarehouse)
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
        axios.delete('/rentalOffice/warehouse/' + recordData.idMovieWarehouse)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idMovieWarehouse == recordData.idMovieWarehouse)
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
