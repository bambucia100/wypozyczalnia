<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Movie" :isForm="isForm"></TheDatabaseNavigation>
    <h1>Add movie</h1>
    <div class="form-wrapper">
      <div class="form-area">
        Movie title:<br/>
        <input type="text" v-model="postObject.title"><br/>
        Length of movie:<br/>
        <input type="text" v-model="postObject.length"><br/>
        Director:<br/>
        <input type="text" v-model="postObject.director"><br/>
        Rating:<br/>
        <input type="text" v-model="postObject.rating"><br/>
        Description:<br/>
        <textarea rows="6" v-model="postObject.description"></textarea><br/>
        Genre:
        <cool-select
          class="cool-select-input"
          v-model="postObject.genreName"
          :items="selectSuggestionsData.genres"/><br/>
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
  import TheDatabaseNavigation from '../components/TheDatabaseNavigation.vue'
  export default {
    name: 'AddMovie',
    components: { TheDatabaseNavigation, CoolSelect },
    data () {
      return {
        postObject: {
          idMovie: '',
          title: '',
          length: '',
          director: '',
          rating: '',
          description: '',
          genreName: '',
        },
        selectSuggestionsData: {
          genres: [],
        },
        cleanObject: {},
        alertText: '',
        isForm: true
      }
    },
    created () {
      this.cleanObject = _.cloneDeep(this.postObject)
      axios.get('/genre/all')
        .then(response => {
          let i
          for (i = 0; i < response.data.length; i++) {
            this.selectSuggestionsData.genres.push(response.data[i].genreName)
          }
        })
    },
    methods: {
      postObjectToDatabase () {
        if(this.postObject.genreName == null) {
          axios.post('/movie', this.postObject)
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
        }
        else {
          axios.post('/movie/' + this.postObject.genreName, this.postObject)
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
        }
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
