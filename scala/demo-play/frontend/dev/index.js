import {Vue, fetch, jQuery, $} from "./lib/util";

console.log("$:jQuery:" + jQuery + " " + $);

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
            if (window.confirm("确定执行吗？")) this.message = this.message.split('').reverse().join('');
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
        ajax: () => {
            fetch('/data/hello.json').then(function (response) {
                return response.json();
            }).then(function (data) {
                app6.message = JSON.stringify(data);
            }).catch(function (e) {
                console.log("Exception:" + e);
            });
        },
        html: () => {
            fetch('/nothing.html').then(function (response) {
                return response.text();
            }).then(function (text) {
                app6.content = text;
            }).catch(function (e) {
                console.log("Exception:" + e);
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