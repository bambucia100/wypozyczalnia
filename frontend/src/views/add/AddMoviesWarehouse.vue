<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Warehouse" :isForm="isForm"></TheDatabaseNavigation>
    <h1>Add warehouse entry</h1>
    <div class="form-wrapper">
      <div class="form-area">
        Movie:<br/>
        <cool-select
          class="cool-select-input"
          v-model="postObject.idMovie"
          :items="selectSuggestionsData.movies"
          item-text="titleID"
          item-value="idMovie"/><br/>
        Rental office:<br/>
        <cool-select
          class="cool-select-input"
          v-model="postObject.idRentalOffice"
          :items="selectSuggestionsData.rentalOffices"
          item-text="rentalOfficeNameID"
          item-value="idRentalOffice"/><br/>
        Quantity in warehouse:<br/>
        <input type="text" v-model="postObject.quantity"><br/>
        Daily charge:<br/>
        <input type="text" v-model="postObject.charge"><br/>
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
    name: 'AddMoviesWarehouse',
    components: { TheDatabaseNavigation, CoolSelect },
    data () {
      return {
        postObject: {
          idMovie: '',
          quantity: '',
          charge: '',
          idRentalOffice: ''
        },
        selectSuggestionsData: {
          rentalOffices: [],
          movies: []
        },
        cleanObject: {},
        alertText: '',
        isForm: true
      }
    },
    created () {
      this.cleanObject = _.cloneDeep(this.postObject)
      axios.get('/rentalOffice/all')
        .then(response => {
          function responseConstructor (idRentalOffice, rentalOfficeName) {
            this.idRentalOffice = idRentalOffice
            this.rentalOfficeNameID = rentalOfficeName + ' ID: ' + idRentalOffice
          }
          let i
          for (i = 0; i < response.data.length; i++) {
            this.selectSuggestionsData.rentalOffices.push(new responseConstructor(response.data[i].idRentalOffice, response.data[i].rentalOfficeName))
          }
        })
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
    },
    methods: {
      postObjectToDatabase () {
        axios.post('/rentalOffice/' + this.postObject.idRentalOffice + '/warehouse/' + this.postObject.idMovie, this.postObject)
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
