var $ = jQuery = require("jQuery");

var jsonSubmit = require('math').jsonSubmit;

$(function () {
    $("#bt_1").click(function () {
        jsonSubmit("form_1", function (data) {
            alert(data);
        }, function (xhr, ts, et) {
            alert("1:" + xhr + " " + ts + " " + et);
        });
    });
    $("#bt_2").click(function () {
        jsonSubmit("form_2", function (data) {
            alert(data);
        }, function (xhr, ts, et) {
            alert("1:" + xhr + " " + ts + " " + et);
        });
    });

    $("#bt_3").click(function () {
        var f = $("#form_3");
        $.ajax({
            url: f.attr("action"),
            data: f.serializeObject(),
            type: "GET",
            success: function (data) {
                alert(data);
            },
            error: function (xhr, ts, et) {
                switch (xhr.status) {
                    case 403:
                        alert("1");
                        window.top.location = '/demo/admin';
                        alert("1-1");
                        break;
                    default:
                        alert("2");
                        window.top.location = '/demo';
                        alert("2-2");
                }
            }
        });
    });
});