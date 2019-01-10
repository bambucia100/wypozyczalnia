import Vue from 'vue'
import Router from 'vue-router'
import HomeTitle from './views/HomeTitle.vue'
import Actor from './views/Actor.vue'
import Adress from './views/Adress.vue'
import Genre from './views/Genre.vue'
import Cast from './views/Cast.vue'
import Client from './views/Client.vue'
import Employee from './views/Employee.vue'
import Movie from './views/Movie.vue'
import MoviesWarehouse from './views/MoviesWarehouse.vue'
import RentalOffice from './views/RentalOffice.vue'
import Rentals from './views/Rentals.vue'
import RentalElement from './views/RentalElement.vue'
import AddActor from './views/AddActor.vue'
import AddCast from './views/AddCast.vue'
import AddClient from './views/AddClient.vue'
import AddEmployee from './views/AddEmployee.vue'
import AddGenre from './views/AddGenre.vue'
import AddMovie from './views/AddMovie.vue'
import AddMoviesWarehouse from './views/AddMoviesWarehouse.vue'
import AddRentalOffice from './views/AddRentalOffice.vue'
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
  ]
})
