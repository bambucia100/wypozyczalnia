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
      path: '/rentalOffice/clients',
      name: 'Client',
      component: Client
    },
    {
      path: '/rentalOffice/employees',
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
  ]
})
