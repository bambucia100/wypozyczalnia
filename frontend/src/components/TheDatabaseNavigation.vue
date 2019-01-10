<template>
  <div id="db-nav">
    <router-link :to="isForm ? '/add/actor' : '/actor'" :class="[ isActor ? 'active' : 'router-links' ]">Actors</router-link>
    |
    <router-link :to="isForm ? '/add/adress' : '/adress'" :class="[ isAdress ? 'active' : 'router-links', isForm ? 'text-invisible' : '']">Addresses</router-link>
    <span :class="[isForm ? 'text-invisible' : '']"> | </span>
    <router-link :to="isForm ? '/add/movie/cast' : '/movie/cast'" :class="[ isCast ? 'active' : 'router-links' ]">Casts</router-link>
    |
    <router-link :to="isForm ? '/add/rentalOffice/client' : '/rentalOffice/client'" :class="[ isClient ? 'active' : 'router-links' ]">Clients</router-link>
    |
    <router-link :to="isForm ? '/add/rentalOffice/employee' : '/rentalOffice/employee'" :class="[ isEmployee ? 'active' : 'router-links' ]">Employees</router-link>
    |
    <router-link :to="isForm ? '/add/genre' : '/genre'" :class="[ isGenre ? 'active' : 'router-links' ]">Movie genres</router-link>
    |
    <router-link :to="isForm ? '/add/movie' : '/movie'" :class="[ isMovie ? 'active' : 'router-links' ]">Movies</router-link>
    |
    <router-link :to="isForm ? '/add/rentalOffice/warehouse' : '/rentalOffice/warehouse'" :class="[ isWarehouse ? 'active' : 'router-links' ]">Rental office warehouse</router-link>
    |
    <router-link :to="isForm ? '/add/rentalElement' : '/rentalElement'" :class="[ isRentalElement ? 'active' : 'router-links', isForm ? 'text-invisible' : '' ]">Rental elements</router-link>
    <span :class="[isForm ? 'text-invisible' : '']"> | </span>
    <router-link :to="isForm ? '/add/rentals' : '/rentals'" :class="[ isRental ? 'active' : 'router-links', isForm ? 'text-invisible' : '' ]">Rentals</router-link>
    <span :class="[isForm ? 'text-invisible' : '']"> | </span>
    <router-link :to="isForm ? '/add/rentalOffice' : '/rentalOffice'" :class="[ isRentalOffice ? 'active' : 'router-links' ]">Rental offices</router-link>
  </div>
</template>

<script>
export default {
  name: 'TheDatabaseNavigation',
  props: {
    link: String,
    isForm: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      isActor: false,
      isAdress: false,
      isCast: false,
      isClient: false,
      isEmployee: false,
      isGenre: false,
      isMovie: false,
      isWarehouse: false,
      isRentalElement: false,
      isRentalOffice: false,
      isRental: false
    }
  },
  created () {
    switch (this.link) {
      case 'Actor':
        this.isActor = true
        break
      case 'Adress':
        this.isAdress = true
        break
      case 'Cast':
        this.isCast = true
        break
      case 'Client':
        this.isClient = true
        break
      case 'Employee':
        this.isEmployee = true
        break
      case 'Genre':
        this.isGenre = true
        break
      case 'Movie':
        this.isMovie = true
        break
      case 'Warehouse':
        this.isWarehouse = true
        break
      case 'RentalElement':
        this.isRentalElement = true
        break
      case 'RentalOffice':
        this.isRentalOffice = true
        break
      case 'Rentals':
        this.isRental = true
        break
    }
  }
}
</script>
<style scoped>
  .active {
    color: #FFF;
  }
  .router-links {
    color: #AAA;
  }
  .router-links:hover {
    color: #DDD;
  }
  .text-invisible {
    display: none;
  }
</style>
