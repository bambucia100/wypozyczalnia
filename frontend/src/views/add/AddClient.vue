<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Client" :isForm="isForm"></TheDatabaseNavigation>
    <h1>Add client</h1>
    <div class="form-wrapper">
      <div class="form-area">
        First name:<br/>
        <input type="text" v-model="postObject.client.clientFirstName" required><br/>
        Last name:<br/>
        <input type="text" v-model="postObject.client.clientLastName" required><br/>
        Registration date:<br/>
        <input type="date" v-model="postObject.client.registrationDate"><br/>
        E-mail:<br/>
        <input type="email" v-model="postObject.client.email"><br/>
        Phone number:<br/>
        <input type="text" v-model="postObject.client.number"><br/>
        Rental office:
        <cool-select
          class="cool-select-input"
          v-model="postObject.client.idRentalOffice"
          :items="selectSuggestionsData.rentalOffices"
          item-text="rentalOfficeNameID"
          item-value="idRentalOffice"/><br/>
        Country:<br/>
        <input type="text" v-model="postObject.adress.country"><br/>
        Province:<br/>
        <input type="text" v-model="postObject.adress.province"><br/>
        City:<br/>
        <input type="text" v-model="postObject.adress.city" required><br/>
        Street:<br/>
        <input type="text" v-model="postObject.adress.street" required><br/>
        House number:<br/>
        <input type="number" v-model="postObject.adress.houseNumber" required><br/>
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
    name: 'AddClient',
    components: { TheDatabaseNavigation, CoolSelect },
    data () {
      return {
        postObject: {
          client: {
            clientFirstName: '',
            clientLastName: '',
            registrationDate: '',
            email: '',
            number: '',
            idRentalOffice: '',
            idAdress: ''
          },
          adress: {
            country: '',
            province: '',
            city: '',
            street: '',
            houseNumber: ''
          }
        },
        selectSuggestionsData: {
          rentalOffices: [],
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
    },
    methods: {
      postObjectToDatabase () {
        axios.post('/adress', this.postObject.adress)
          .then(response => {
            this.postObject.client.idAdress = response.data
            axios.post('/rentalOffice/' + this.postObject.client.idRentalOffice + '/clients/' + this.postObject.client.idAdress, this.postObject.client)
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
