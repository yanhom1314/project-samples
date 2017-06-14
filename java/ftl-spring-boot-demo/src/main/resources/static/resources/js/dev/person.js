import {axios, Vue} from "./lib/util";

var items = {};

var table1 = new Vue({
    el: "#table1",
    data: {
        items: items
    },
    beforeMount: function () {
        this.load();
    },
    methods: {
        load: function () {
            var self = this;
            axios.get('/person/all').then(function (response) {
                console.log(response.data);
                self.items = response.data;
            }).catch(function (error) {
                console.log("error:" + error);
            });
        }
    }
});
