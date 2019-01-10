<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Genre" :isForm="isForm"></TheDatabaseNavigation>
    <h1>Add movie genre</h1>
    <div class="form-wrapper">
      <div class="form-area">
        Name:<br/>
        <input type="text" v-model="postObject.genreName"><br/>
        Description:<br/>
        <textarea rows="6" v-model="postObject.genreDescription"></textarea><br/>
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
    name: 'AddGenre',
    components: { TheDatabaseNavigation },
    data () {
      return {
        postObject: {
          genreName: '',
          genreDescription: '',
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
        axios.post('/genre', this.postObject)
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
