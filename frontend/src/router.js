import Vue from 'vue'
import Router from 'vue-router'
import HomeTitle from './views/HomeTitle.vue'
import Actor from './views/database/Actor.vue'
import Adress from './views/database/Adress.vue'
import Genre from './views/database/Genre.vue'
import Cast from './views/database/Cast.vue'
import Client from './views/database/Client.vue'
import Employee from './views/database/Employee.vue'
import Movie from './views/database/Movie.vue'
import MoviesWarehouse from './views/database/MoviesWarehouse.vue'
import RentalOffice from './views/database/RentalOffice.vue'
import Rentals from './views/database/Rentals.vue'
import RentalElement from './views/database/RentalElement.vue'
import AddActor from './views/add/AddActor.vue'
import AddCast from './views/add/AddCast.vue'
import AddClient from './views/add/AddClient.vue'
import AddEmployee from './views/add/AddEmployee.vue'
import AddGenre from './views/add/AddGenre.vue'
import AddMovie from './views/add/AddMovie.vue'
import AddMoviesWarehouse from './views/add/AddMoviesWarehouse.vue'
import AddRentalOffice from './views/add/AddRentalOffice.vue'
import RentPackage from './views/rent/RentPackage.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HomeTitle',
      component: HomeTitle
    },
    {
      path: '/actor',
      name: 'Actor',
      component: Actor
    },
    {
      path: '/adress',
      name: 'Adress',
      component: Adress
    },
    {
      path: '/movie/cast',
      name: 'Cast',
      component: Cast
    },
    {
      path: '/genre',
      name: 'Genre',
      component: Genre
    },
    {
      path: '/rentalOffice/client',
      name: 'Client',
      component: Client
    },
    {
      path: '/rentalOffice/employee',
      name: 'Employee',
      component: Employee
    },
    {
      path: '/movie',
      name: 'Movie',
      component: Movie
    },
    {
      path: '/rentalOffice/warehouse',
      name: 'MoviesWarehouse',
      component: MoviesWarehouse
    },
    {
      path: '/rentalOffice',
      name: 'RentalOffice',
      component: RentalOffice
    },
    {
      path: '/rentals',
      name: 'Rentals',
      component: Rentals
    },
    {
      path: '/rentalElement',
      name: 'RentalElement',
      component: RentalElement
    },
    {
      path: '/add/actor',
      name: 'AddActor',
      component: AddActor
    },
    {
      path: '/add/movie/cast',
      name: 'AddCast',
      component: AddCast
    },
    {
      path: '/add/rentalOffice/client',
      name: 'AddClient',
      component: AddClient
    },
    {
      path: '/add/rentalOffice/employee',
      name: 'AddEmployee',
      component: AddEmployee
    },
    {
      path: '/add/genre',
      name: 'AddGenre',
      component: AddGenre
    },
    {
      path: '/add/movie',
      name: 'AddMovie',
      component: AddMovie
    },
    {
      path: '/add/rentalOffice/warehouse',
      name: 'AddMoviesWarehouse',
      component: AddMoviesWarehouse
    },
    {
      path: '/add/rentalOffice',
      name: 'AddRentalOffice',
      component: AddRentalOffice
    },
    {
      path: '/rent',
      name: 'RentPackage',
      component: RentPackage
    },
  ]
})
