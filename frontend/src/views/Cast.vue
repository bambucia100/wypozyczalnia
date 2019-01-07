<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Cast"></TheDatabaseNavigation>
    <h1>Casts</h1>
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
        ID Movie:<br/>
        <input type="text" v-model="newRecordData.idMovie" disabled><br/>
        Movie title:<br/>
        <input type="text" v-model="newRecordData.title" disabled><br/>
        ID Actor:<br/>
        <input type="text" v-model="newRecordData.idActor" disabled><br/>
        Actor's name:<br/>
        <input type="text" v-model="newRecordData.name" disabled><br/>
        Roles:<br/>
        <input type="text" v-model="newRecordData.characters"><br/>
        Role play rating:<br/>
        <input type="text" v-model="newRecordData.actorRating"><br/>
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
  import _ from 'lodash'

  export default {
    name: 'Cast',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'idMovie', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'title', name: 'Title', filterable: true, sortable: true },
          { prop: 'idActor', name: 'ID', width: 36, numberFilter: true, sortable: true },
          { prop: 'name', name: 'Name', filterable: true, sortable: true },
          { prop: 'characters', name: 'Roles', searchable: true },
          { prop: 'actorRating', name: 'Rating', width: 60, numberFilter: true, sortable: true },
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        oldRecordData: {},
        newRecordData: {},
        alertText: ''
      }
    },
    created () {
      axios.get('/movie/cast')
        .then(response => {
          function responseConstructor(idMovie, title, idActor, actorFirstName, actorLastName, characters, actorRating){
            this.idMovie = idMovie
            this.title = title
            this.idActor = idActor
            this.name = actorFirstName + ' ' + actorLastName
            this.characters = characters
            this.actorRating = actorRating
          }
          let i
          for(i=0; i<response.data.length; i++) {
            this.tableData.push(new responseConstructor(response.data[i].movie.idMovie, response.data[i].movie.title, response.data[i].actor.idActor,
              response.data[i].actor.actorFirstName, response.data[i].actor.actorLastName, response.data[i].characters, response.data[i].actorRating))
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
        axios.put('/movie/cast/' + this.oldRecordData.idMovie + '/' + this.oldRecordData.idActor, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem =>
              (tableElem.idMovie == this.oldRecordData.idMovie && tableElem.idActor == this.oldRecordData.idActor))
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
        axios.delete('/movie/cast/' + recordData.idMovie + '/' + recordData.idActor)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableElem =>
              (tableElem.idMovie == recordData.idMovie && tableElem.idActor == recordData.idActor))
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
