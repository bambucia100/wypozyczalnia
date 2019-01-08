<template>
  <div class="db-data">
    <TheDatabaseNavigation link="RentalOffice"></TheDatabaseNavigation>
    <h1>Rental offices</h1>
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
    <form-modal name="editModal" :width="600" :height="350">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID Rental office:<br/>
        <input type="text" v-model="newRecordData.idRentalOffice" disabled><br/>
        Rental office name:<br/>
        <input type="text" v-model="newRecordData.rentalOfficeName"><br/>
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
  name: 'RentalOffice',
  components: {
    TheDatabaseNavigation,
    VueVirtualTable
  },
  data () {
    return {
      tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
        { prop: 'idRentalOffice', name: 'ID', width: 36, sortable: true, numberFilter: true },
        { prop: 'rentalOfficeName', name: 'Office name', width: 300, searchable: true, sortable: true },
        { prop: 'idAdress', name: 'ID', width: 36, sortable: true, numberFilter: true },
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
    axios.get('/rentalOffice/all')
      .then(response => {
        function responseConstructor (idRentalOffice, rentalOfficeName, idAdress, city, street, houseNumber) {
          this.idRentalOffice = idRentalOffice
          this.rentalOfficeName = rentalOfficeName
          this.idAdress = idAdress
          this.adressComplete = city + ', ' + street + ' ' + houseNumber
        }
        let i
        for (i = 0; i < response.data.length; i++) {
          this.tableData.push(new responseConstructor(response.data[i].idRentalOffice, response.data[i].rentalOfficeName,
            response.data[i].adress.idAdress, response.data[i].adress.city, response.data[i].adress.street, response.data[i].adress.houseNumber))
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
      axios.put('/rentalOffice/' + this.oldRecordData.idRentalOffice + '/' + this.oldRecordData.idAdress, this.newRecordData)
        .then(response => {
          this.$modal.show('alertModal', { text: 'Operation succeeded.' })
          this.$modal.hide('editModal')
          let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idRentalOffice == this.oldRecordData.idRentalOffice)
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
      axios.delete('/rentalOffice/' + recordData.idRentalOffice)
        .then(() => {
          this.$modal.show('alertModal', { text: 'Operation succeeded.' })
          let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idRentalOffice == recordData.idRentalOffice)
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
