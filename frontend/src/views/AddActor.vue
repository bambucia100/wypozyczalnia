<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Actor" :isForm="isForm"></TheDatabaseNavigation>
    <h1>Add actor:</h1>
    <div class="form-wrapper">
      <div class="form-area">
        First name:<br/>
        <input type="text" v-model="postObject.actorFirstName"><br/>
        Last name:<br/>
        <input type="text" v-model="postObject.actorLastName"><br/>
        Date of birth:<br/>
        <input type="date" v-model="postObject.dateOfBirth"><br/>
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
import TheDatabaseNavigation from '../components/TheDatabaseNavigation.vue'
export default {
  name: 'AddActor',
  components: { TheDatabaseNavigation },
  data () {
    return {
      postObject: {
        actorFirstName: '',
        actorLastName: '',
        dateOfBirth: ''
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
      axios.post('/actor/', this.postObject)
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
