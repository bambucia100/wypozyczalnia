<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Cast" :isForm="isForm"></TheDatabaseNavigation>
    <h1>Add cast entry</h1>
    <div class="form-wrapper">
      <div class="form-area">
        Movie title:<br/>
        <cool-select
          class="cool-select-input"
          v-model="postObject.idMovie"
          :items="selectSuggestionsData.movies"
          item-text="titleID"
          item-value="idMovie"/><br/>
        Actor's name:<br/>
        <cool-select
          class="cool-select-input"
          v-model="postObject.idActor"
          :items="selectSuggestionsData.actors"
          item-text="actorNameID"
          item-value="idActor"/><br/>
        Roles:<br/>
        <input type="text" v-model="postObject.characters"><br/>
        Role play rating:<br/>
        <input type="text" v-model="postObject.actorRating"><br/>
        <button @click="clearInputData()">Clear</button>
        <button @click="postObjectToDatabase()" style="float: right;">Submit</button>
      </div>
      <form-modal name="alertModal" @before-open="beforeOpenAlert" :height="$alertHeight" :width="$alertWidth">
        <div class="modal-box alert-modal-box">
          {{alertText}}
          <button @click="$modal.hide('alertModal')">Close</button>
        </div>
      </form-modal>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import { CoolSelect } from 'vue-cool-select'
import TheDatabaseNavigation from '../../components/TheDatabaseNavigation.vue'
export default {
  name: 'AddCast',
  components: { TheDatabaseNavigation, CoolSelect },
  data () {
    return {
      postObject: {
        idMovie: '',
        title: '',
        idActor: '',
        name: '',
        characters: '',
        actorRating: ''
      },
      selectSuggestionsData: {
        movies: [],
        actors: []
      },
      cleanObject: {},
      alertText: '',
      isForm: true
    }
  },
  created () {
    this.cleanObject = _.cloneDeep(this.postObject)
    axios.get('/movie/all')
      .then(response => {
        function responseConstructor (idMovie, title) {
          this.idMovie = idMovie
          this.titleID = title + ' ID: ' + idMovie
        }
        let i
        for (i = 0; i < response.data.length; i++) {
          this.selectSuggestionsData.movies.push(new responseConstructor(response.data[i].idMovie, response.data[i].title))
        }
      })
    axios.get('/actor/all')
      .then(response => {
        function responseConstructor (idActor, actorFirstName, actorLastName) {
          this.idActor = idActor
          this.actorNameID = actorFirstName + ' ' + actorLastName + ' ID: ' + idActor
        }
        let i
        for (i = 0; i < response.data.length; i++) {
          this.selectSuggestionsData.actors.push(new responseConstructor(response.data[i].idActor, response.data[i].actorFirstName, response.data[i].actorLastName))
        }
      })
  },
  methods: {
    postObjectToDatabase () {
      axios.post('/movie/cast/' + this.postObject.idMovie + '/' + this.postObject.idActor, this.postObject)
        .then(response => {
          this.$modal.show('alertModal', { text: 'Record added to database.' })
          this.postObject = _.cloneDeep(this.cleanObject)
        })
        .catch(error => {
          if (error.response) {
            this.$modal.show('alertModal',
              { text: 'Operation failed.  |  ' + error.response.status + '  |  ' + error.response.data.error + '  |  ' + error.response.data.message })
          }
        })
    },
    clearInputData () {
      this.postObject = _.cloneDeep(this.cleanObject)
    },
    beforeOpenAlert (event) {
      this.alertText = event.params.text
    }
  }
}
</script>

<style scoped>

</style>
