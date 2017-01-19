import fetch from 'fetch';
import Vue from 'vue';
import VeeValidate from 'vee-validate';
import messagesCN from 'vee-locale-cn';

const config = {
    errorBagName: 'errors', // change if property conflicts.
    fieldsBagName: 'fields',
    delay: 0,
    locale: 'cn',
    dictionary: {
        cn: {
            messages: messagesCN
        }
    }
};
Vue.use(VeeValidate, config);

//
// console.log("$:" + $.ajax() + " " + jQuery.ajax());
// console.log("u:" + new u({
//         el: '#app',
//         data: {
//             message: 'Hello Vue!'
//         }
//     }));
// console.log("VeeValidate:"+VeeValidate);
// console.log("f:" + f('/data/hello.json').then(function (response) {
//         return response.json();
//     }).then(function (data) {
//         app6.message = JSON.stringify(data);
//     }).catch(function (e) {
//         console.log("Oops, error");
//     }));

var app = new Vue({
    el: '#app',
    data: {
        message: 'Hello Vue!'
    }
});

var app1 = new Vue({
    el: '#app1',
    data: {
        message: 'Hello Vue in app-1!'
    }
});

var app2 = new Vue({
    el: '#app-2',
    data: {
        message: 'You loaded this page on ' + new Date()
    }
});
var app3 = new Vue({
    el: '#app-3',
    data: {
        seen: true
    }
});
var app4 = new Vue({
    el: '#app-4',
    data: {
        list: [
            {text: 'Learn JavaScript'},
            {text: 'Learn Vue'},
            {text: 'Build something awesome'}
        ]
    }
});
var app5 = new Vue({
    el: '#app-5',
    data: {
        message: 'Hello Vue.js!'
    },
    methods: {
        reverseMessage: function () {
            if (window.confirm("确定执行吗？"))
                this.message = this.message.split('').reverse().join('');
        }
    }
});

var app6 = new Vue({
    el: '#app-6',
    data: {
        message: 'Hello Vue!',
        content: "<h1>Hello</h1>"
    },
    methods: {
        toUpper: function () {
            if (window.confirm("To upper!"))
                this.message = this.message.toUpperCase();
        },
        toLower: function () {
            if (window.confirm("To lower!"))
                this.message = this.message.toLowerCase();
        },
        ajax: function () {
            var msg = this.message;
            fetch('/data/hello.json').then(function (response) {
                return response.json();
            }).then(function (data) {
                app6.message = JSON.stringify(data);
            }).catch(function (e) {
                console.log("Oops, error");
            });
        },
        html: function () {
            var msg = this.message;
            fetch('/nothing.html').then(function (response) {
                return response.text();
            }).then(function (text) {
                app6.content = text;
            }).catch(function (e) {
                console.log("Oops, error");
            });
        }
    }
});

// Define a new component called todo-item
Vue.component('todo-item', {
    props: ['todo'],
    template: '<li>{{ todo.text }}</li>'
});

var app7 = new Vue({
    el: '#app-7',
    data: {
        list: [
            {text: 'Vegetables'},
            {text: 'Cheese'},
            {text: 'Whatever else humans are supposed to eat'}
        ]
    }
});

var app8 = new Vue({
    el: "#app8"
});

//window.onload
$(function () {
    $("#_close").click(function () {
        console.log("_close");
    });
    $("#_save").click(function () {
        console.log("_save");
    });
});