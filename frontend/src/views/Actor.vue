<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Actor"></TheDatabaseNavigation>
    <h1>Actors</h1>
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
    <form-modal name="editModal" :width="600" :height="330">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID:<br/>
        <input type="text" v-model="newRecordData.idActor" disabled><br/>
        First name:<br/>
        <input type="text" v-model="newRecordData.actorFirstName"><br/>
        Last name:<br/>
        <input type="text" v-model="newRecordData.actorLastName"><br/>
        Date of birth:<br/>
        <input type="date" v-model="newRecordData.dateOfBirth"><br/>
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
  name: 'Actor',
  components: {
    TheDatabaseNavigation,
    VueVirtualTable
  },
  data () {
    return {
      tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
        { prop: 'idActor', name: 'ID', width: 36, sortable: true, numberFilter: true },
        { prop: 'actorFirstName', name: 'First name', searchable: true, sortable: true },
        { prop: 'actorLastName', name: 'Last name', searchable: true, sortable: true },
        { prop: 'dateOfBirth', name: 'Date of birth', searchable: true, sortable: true, width: 150 },
        { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
      ],
      tableData: [],
      oldRecordData: {},
      newRecordData: {},
      alertText: ''
    }
  },
  created () {
    axios.get('/actor/all')
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
      axios.put('/actor/' + this.oldRecordData.idActor, this.newRecordData)
        .then(response => {
          this.$modal.show('alertModal', { text: 'Operation succeeded.' })
          this.$modal.hide('editModal')
          let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idActor == this.oldRecordData.idActor)
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
      axios.delete('/actor/' + recordData.idActor)
        .then(() => {
          this.$modal.show('alertModal', { text: 'Operation succeeded.' })
          let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idActor == recordData.idActor)
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
