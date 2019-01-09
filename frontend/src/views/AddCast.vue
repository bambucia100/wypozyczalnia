<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Cast" isForm="true"></TheDatabaseNavigation>
    <h1>Add cast:</h1>
    <div class="form-wrapper">
      <div class="form-area">
        ID Movie:<br/>
        <input type="text" v-model="postObject.idMovie" disabled><br/>
        Movie title:<br/>
        <input type="text" v-model="postObject.title" disabled><br/>
        ID Actor:<br/>
        <input type="text" v-model="postObject.idActor" disabled><br/>
        Actor's name:<br/>
        <input type="text" v-model="postObject.name" disabled><br/>
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
  import TheDatabaseNavigation from '../components/TheDatabaseNavigation.vue'
  export default {
    name: 'AddCast',
    components: { TheDatabaseNavigation },
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
        cleanObject: {},
        alertText: ''
      }
    },
    created () {
      cleanObject = _.cloneDeep(postObject)
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
