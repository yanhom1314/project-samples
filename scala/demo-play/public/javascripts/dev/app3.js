var $ = jQuery = require("jquery");

require("bootstrap-css");
require("main-css");

var add = require('math').add;

$(function () {
    $("#bt_1").click(function () {
        $.jsonSubmit("form_1", function (data) {
            alert(data);
        });
    });
    $("#bt_2").click(function () {
        $.jsonSubmit("form_2", function (data) {
            alert(data);
        }, function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        });
    });

    $("#bt_3").click(function () {
        var f3 = $("#form_3");
        $.get("/demo/check/" + f3.serializeObject().name, function (d) {
            alert(d);
        });
    });
});