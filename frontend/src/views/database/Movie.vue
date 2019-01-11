<template>
  <div class="db-data">
    <TheDatabaseNavigation link="Movie"></TheDatabaseNavigation>
    <h1>Movies</h1>
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
    <form-modal name="editModal" :width="600" :height="600">
      <div class="modal-box">
        <span class="modal-box-title">Edit record:</span>
        ID Client:<br/>
        <input type="text" v-model="newRecordData.idMovie" disabled><br/>
        Movie title:<br/>
        <input type="text" v-model="newRecordData.title"><br/>
        Length of movie:<br/>
        <input type="text" v-model="newRecordData.length"><br/>
        Director:<br/>
        <input type="text" v-model="newRecordData.director"><br/>
        Rating:<br/>
        <input type="text" v-model="newRecordData.rating"><br/>
        Description:<br/>
        <textarea rows="6" v-model="newRecordData.description"></textarea><br/>
        Genre:<br/>
        <input type="text" v-model="newRecordData.genreName"><br/> <!--TODO cool select-->
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
    name: 'Movie',
    components: {
      TheDatabaseNavigation,
      VueVirtualTable
    },
    data () {
      return {
        tableConfig: [/* prop, name, width, sortable, searchable, filterable, numberFilter, summary, prefix, suffix */
          { prop: 'idMovie', name: 'ID', width: 36, sortable: true, numberFilter: true },
          { prop: 'title', name: 'Title', width: 200, searchable: true, sortable: true },
          { prop: 'director', name: 'Director', width: 170, filterable: true, sortable: true },
          { prop: 'genreName', name: 'Genre', width: 130, sortable: true, filterable: true },
          { prop: 'length', name: 'Length', width: 70, numberFilter: true, sortable: true, suffix: 'min.'},
          { prop: 'rating', name: 'Rating', width: 60, numberFilter: true , sortable: true},
          { prop: 'description', name: 'Description', searchable: true },
          { prop: '_action', name: 'Action', actionName: 'actionCommon', width: 130 }
        ],
        tableData: [],
        oldRecordData: {},
        newRecordData: {},
        alertText: ''
      }
    },
    created () {
      axios.get('/movie/all')
        .then(response => {
          function responseConstructor(idMovie, title, length, director, rating, description, genreName){
            this.idMovie = idMovie
            this.title = title
            this.length = length
            this.director = director
            this.rating = rating
            this.description = description
            this.genreName = genreName
          }
          let i
          for(i=0; i<response.data.length; i++) {
            this.tableData.push(new responseConstructor(response.data[i].idMovie, response.data[i].title, response.data[i].length,
              response.data[i].director, response.data[i].rating,  response.data[i].description, response.data[i].genre.genreName))
          }
        })
    },
    methods: {
      editRecord: function (recordData) {
        this.$modal.show('editModal')
        this.oldRecordData = recordData
        this.newRecordData = recordData
      },
      editRecordSubmit: function () { // /movie/{idMovie}/{genreName}
        axios.put('/movie/' + this.oldRecordData.idMovie + '/' + this.oldRecordData.genreName, this.newRecordData)
          .then(response => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            this.$modal.hide('editModal')
            let editElemIndex = this.tableData.findIndex(tableElem => tableElem.idMovie == this.oldRecordData.idMovie)
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
        axios.delete('/movie/' + recordData.idMovie)
          .then(() => {
            this.$modal.show('alertModal', { text: 'Operation succeeded.' })
            let deleteElemIndex = this.tableData.findIndex(tableDataElem => tableDataElem.idMovie == recordData.idMovie)
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
