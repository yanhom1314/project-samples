require('../../css/main.css'); //加载初始化样式
var $= require('jquery')
var add = require('math').add;
$(function() {        
    console.log("Hello World! Add 5 6:"+add(5,6));
    console.log($("#message").html());    
});
