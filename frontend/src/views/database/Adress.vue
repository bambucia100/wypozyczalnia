<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Adress"></TheDatabaseNavigation>
    <h1>Addresses</h1>
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
    <form-modal name="editModal" :width="600" :height="440">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID:<br/>
        <input type="text" v-model="newRecordData.idAdress" disabled><br/>
        Country:<br/>
        <input type="text" v-model="newRecordData.country"><br/>
        Province:<br/>
        <input type="text" v-model="newRecordData.province"><br/>
        City:<br/>
        <input type="text" v-model="newRecordData.city"><br/>
        Street:<br/>
        <input type="text" v-model="newRecordData.street"><br/>
        House number:<br/>
        <input type="text" v-model="newRecordData.houseNumber"><br/>
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
  name: 'Adress',
  components: {
    TheDatabaseNavigation,
    VueVirtualTable
  },
  data () {
    return {
      tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
        { prop: 'idAdress', name: 'ID', width: 36, sortable: true, numberFilter: true },
        { prop: 'country', name: 'Country', width: 280, filterable: true, sortable: true },
        { prop: 'province', name: 'Province', width: 210, filterable: true, sortable: true },
        { prop: 'city', name: 'City', filterable: true, sortable: true },
        { prop: 'street', name: 'Street', width: 245, searchable: true, sortable: true },
        { prop: 'houseNumber', name: 'Number', width: 66, numberFilter: true, sortable: true },
        { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
      ],
      tableData: [],
      oldRecordData: {},
      newRecordData: {},
      alertText: ''
    }
  },
  created () {
    axios.get('/adress')
      .then(response => {
        this.tableData = response.data
      })
  },
  methods: {
    editRecord: function (recordData) {
      this.$modal.show('editModal')
      this.oldRecordData = recordData
      this.newRecordData = recordData
    },
    editRecordSubmit: function () {
      axios.put('/adress/' + this.oldRecordData.idAdress, this.newRecordData)
        .then(response => {
          this.$modal.show('alertModal', { text: 'Operation succeeded.' })
          this.$modal.hide('editModal')
          let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idAdress == this.oldRecordData.idAdress)
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
      axios.delete('/adress/' + recordData.idAdress)
        .then(() => {
          this.$modal.show('alertModal', { text: 'Operation succeeded.' })
          let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idAdress == recordData.idAdress)
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
