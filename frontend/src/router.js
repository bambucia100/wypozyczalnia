import Vue from 'vue'
import Router from 'vue-router'
import Genre from './views/Genre.vue'
import HomeTitle from './views/HomeTitle.vue'
import Actor from './views/Actor.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HomeTitle',
      component: HomeTitle
    },
    {
      path: '/genre',
      name: 'Genre',
      component: Genre
    },
    {
      path: '/actor',
      name: 'Actor',
      component: Actor
    },
  ]
})
