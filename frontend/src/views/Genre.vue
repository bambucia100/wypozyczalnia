<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Genre"></TheDatabaseNavigation>
    <h1>Movie genres</h1>
    <vue-virtual-table
      class="v-table"
      :config="tableConfig"
      :data="tableData"
      :minWidth="800"
      :height="510"
      :itemHeight="34"
      :bordered="true"
      :selectable="true"
    >
      <template slot-scope="scope" slot="actionCommon">
        <button @click="editRecord(scope.row)">Edit</button>
        <button @click="deleteRecord(scope.row)">Delete</button>
      </template>
    </vue-virtual-table>
    <form-modal name="editModal">
      <div class="modal-box">
        <h6>Edit record:</h6>
        Name:<br/>
        <input type="text" v-model="newRecordData.genreName"><br/>
        Description:<br/>
        <textarea rows="6" v-model="newRecordData.genreDescription"></textarea><br/>
        <button @click="editRecordSubmit">Submit</button><button @click="$modal.hide('editModal')" style="float:right">Cancel</button>
      </div>
    </form-modal>
    <form-modal name="alertModal" @before-open="beforeOpenAlert" height="200">
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
  name: 'Genre',
  components: {
    TheDatabaseNavigation,
    VueVirtualTable
  },
  data: function () {
    return {
      tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
        { prop: 'genreName', name: 'Name', width: 200, sortable: true, searchable: true },
        { prop: 'genreDescription', name: 'Description', searchable: true },
        { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
      ],
      tableData: [],
      oldRecordData: {},
      newRecordData: {},
      alertText: ''
    }
  },
  created () {
    axios.get('/genre/all')
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
      let self = this
      axios.put('/genre/' + this.oldRecordData.genreName, this.newRecordData)
        .then(function (response) {
          self.$modal.show('alertModal', { text: 'Operation succeeded.' })
          self.$modal.hide('editModal')
          let editElemIndex = self.tableData.indexOf(self.tableData.find(tableDataElem => tableDataElem.genreName == self.oldRecordData.genreName))
          self.$set(self.tableData, editElemIndex, self.newRecordData)
        })
        .catch(error => {
          if (error.response) {
            self.$modal.show('alertModal',
              { text: 'Operation failed.  |  ' + error.response.status + '  |  ' + error.response.data.error + '  |  ' + error.response.data.message })
          }
        })
    },
    deleteRecord: function (recordData) {
      let self = this
      axios.delete('/genre/' + recordData.genreName)
        .then(function () {
          self.$modal.show('alertModal', { text: 'Operation succeeded.' })
          let deleteElemIndex = self.tableData.indexOf(self.tableData.find(tableDataElem => tableDataElem.genreName == recordData.genreName))
          self.tableData.splice(deleteElemIndex, 1)
        })
        .catch(error => {
          if (error.response) {
            self.$modal.show('alertModal',
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
