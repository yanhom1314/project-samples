var $ = jQuery = require("jquery");

var jsonSubmit = require('math').jsonSubmit;

$(function () {
    $("#bt_1").click(function () {
        jsonSubmit("form_1", function (data) {
            alert(data);
        });
    });
    $("#bt_2").click(function () {
        jsonSubmit("form_2", function (data) {
            alert(data);
        }, function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest.status);
            alert(XMLHttpRequest.readyState);
            alert(textStatus);
        });
    });

    $("#bt_3").click(function () {
        var f3 = $("#form_3");
        $.get("/demo/is/" + f3.serializeObject().name, function (d) {
            alert(d);
        });
    });
});