import $ from "jquery";

$.fn._add = function (x, y) {
    return x + y;
};

var d3 = window.d3 || {};

d3._add = function (x, y) {
    return x + y;
};

export {d3}