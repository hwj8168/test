// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'
import VueAxios from 'vue-axios'
import iView from 'iview';
import 'iview/dist/styles/iview.css';

Vue.config.productionTip = false
Vue.use(VueAxios, axios);
Vue.use(iView);

axios.interceptors.request.use(
  config => {
    if (localStorage.getItem("token")) {
      config.headers.common['token'] = localStorage.getItem("token");
    }
    return config
  },
  err => {
    return Promise.reject(err)
  })
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: {App},
  template: '<App/>'
})

