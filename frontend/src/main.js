import Vue from 'vue'
import App from './App.vue'
import router from './router'
import VModal from 'vue-js-modal'

Vue.config.productionTip = false

Vue.prototype.$tableHeight = 510
Vue.prototype.$alertHeight = 500 //150
Vue.prototype.$alertWidth = 500 //400
Vue.use(VModal, { componentName: 'form-modal' })

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')