var $ = jQuery = require("jquery");

require("bootstrap-css");
require("main-css");

var add = require('math').add;

$(function () {
    //alert("Hello World!:1+1=" + add(1, 1));
    $("#bt_1").click(function () {
        var f1 = $("#form_1");
        //alert(f2.attr("action"));
        $.ajax({
            url: "/demo/json",
            type: "POST",
            dataType: "json",
            contentType: 'application/json',
            //data: JSON.stringify(f1.serializeArray()),
            //data: JSON.stringify({un: "admin"}),
            data: JSON.stringify(f1.serialize()),
            success: function (data) {
                alert(data);
            }
        });
    });
    $("#bt_2").click(function () {
        var f2 = $("#form_2");
        //alert(f2.attr("action"));
        $.ajax({
            url: "/demo/check3",
            data: JSON.stringify(f2.serializeArray()),
            type: "POST",
            dataType: "JSON",
            contentType: 'application/json',
            success: function (data) {
                alert(data);
            }
        });
    });
});