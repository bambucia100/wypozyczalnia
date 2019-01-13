<template>
  <div class="add-data">
    <TheDatabaseNavigation link="RentalOffice" :isForm="isForm"></TheDatabaseNavigation>
    <h1>Add rental office</h1>
    <div class="form-wrapper">
      <div class="form-area">
        Rental office name:<br/>
        <input type="text" v-model="postObject.rentalOffice.rentalOfficeName" required><br/>
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
  import TheDatabaseNavigation from '../../components/TheDatabaseNavigation.vue'
  export default {
    name: 'AddRentalOffice',
    components: { TheDatabaseNavigation },
    data () {
      return {
        postObject: {
          rentalOffice: {
            rentalOfficeName: '',
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
        cleanObject: {},
        alertText: '',
        isForm: true
      }
    },
    created () {
      this.cleanObject = _.cloneDeep(this.postObject)
    },
    methods: {
      postObjectToDatabase () {
        axios.post('/adress', this.postObject.adress)
          .then(response => {
            this.postObject.rentalOffice.idAdress = response.data
            axios.post('/rentalOffice/' + this.postObject.rentalOffice.idAdress, this.postObject.rentalOffice)
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
