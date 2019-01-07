<template>
  <div id="db-nav">
    <router-link to="/actor" :class="[ isActor ? 'active' : 'router-links' ]">Actors</router-link>
    |
    <router-link to="/adress" :class="[ isAdress ? 'active' : 'router-links' ]">Addresses</router-link>
    |
    <router-link to="/movie/cast" :class="[ isCast ? 'active' : 'router-links' ]">Casts</router-link>
    |
    <router-link to="/rentalOffice/clients" :class="[ isClient ? 'active' : 'router-links' ]">Clients</router-link>
    |
    <router-link to="/rentalOffice/employees" :class="[ isEmployee ? 'active' : 'router-links' ]">Employees</router-link>
    |
    <router-link to="/genre" :class="[ isGenre ? 'active' : 'router-links' ]">Movie genres</router-link>
    |
    <router-link to="/rentalOffice/warehouse" :class="[ isWarehouse ? 'active' : 'router-links' ]">Rental office warehouse</router-link>
    |
    <router-link to="/rentalElement" :class="[ isRentalElement ? 'active' : 'router-links' ]">Rental elements</router-link>
    |
    <router-link to="/rentalOffice" :class="[ isRentalOffice ? 'active' : 'router-links' ]">Rental offices</router-link>
    |
    <router-link to="/rentals" :class="[ isRental ? 'active' : 'router-links' ]">Rentals</router-link>
  </div>
</template>

<script>
export default {
  name: 'TheDatabaseNavigation',
  props: {
    link: String
  },
  data () {
    return {
      isActor: false,
      isAdress: false,
      isCast: false,
      isClient: false,
      isEmployee: false,
      isGenre: false,
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
</style>
