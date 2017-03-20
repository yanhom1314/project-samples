import Vue from 'Vue'
import App from './App.vue'

/* eslint-disable no-new */
new Vue({
    el: '#app_1',
    render: h => h(App)
    //也可以使用components:{"node-name":"component-name"} html:<node-name></node-name>
});