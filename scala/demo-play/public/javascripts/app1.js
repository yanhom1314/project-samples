var $ = jQuery = require("jquery");

require("main");

var add = require('math').add;

window.onload = function () {
    console.log("Hello World!");
    console.log("jQuery:"+$);
    console.log(add(12, 7));
    console.log($("body").html());
};
