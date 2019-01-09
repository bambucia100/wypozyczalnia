<template>
  <div class="add-data">
    <TheDatabaseNavigation link="Adress" isForm="true"></TheDatabaseNavigation>
    <h1>Add address:</h1>
  </div>
</template>

<script>
  import axios from 'axios'
  import TheDatabaseNavigation from '../components/TheDatabaseNavigation.vue'
  export default {
    name: 'AddAdress',
    components: { TheDatabaseNavigation }
  }
</script>

<style scoped>

</style>
