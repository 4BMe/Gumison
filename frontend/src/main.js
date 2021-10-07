import Vue from "vue";
import App from "./App.vue";
import BootstrapVue from "bootstrap-vue";
import Vuelidate from "vuelidate";
import router from "./router";
import "@/assets/scss/app.scss";
import i18n from "./i18n";
import store from "./store";
import Alertify from "vue-alertify";

Vue.config.productionTip = false;

Vue.use(BootstrapVue);
Vue.use(Vuelidate);
Vue.use(Alertify);

new Vue({
  router,
  store,
  i18n,
  render: (h) => h(App),
}).$mount("#app");
