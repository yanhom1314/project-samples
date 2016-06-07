require('../../css/main.css'); //加载初始化样式
var $ = require('jquery')
require("jquery-validation");
var add = require('math').add;
var check = require('form').check;

$(function () {
    console.log("Hello World! Add 5 6:" + add(5, 6));
    console.log($("#message").html());
   
    check("_form");
});
