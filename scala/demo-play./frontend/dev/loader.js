import {Vue} from "./lib/util";
import App from './App.vue'

Vue.component('modal', {
    template: '#modal-template'
});

new Vue({
    el: '#app',
    data: {
        showModal: false
    }
});

new Vue({
    el: '#app_1',
    render: h => h(App)
});
