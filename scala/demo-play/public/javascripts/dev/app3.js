var $ = jQuery = require("jquery");

require("bootstrap-css");
require("main-css");

var add = require('math').add;

$(function () {
    $.fn.serializeObject = function () {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
    //alert("Hello World!:1+1=" + add(1, 1));
    $("#bt_1").click(function () {
        var f1 = $("#form_1");

        alert(JSON.stringify(f1.serializeObject()));
        $.ajax({
            //url: "/demo/json",
            url: f1.attr("action"),
            type: "POST",
            dataType: "json",
            contentType: 'application/json',
            //data: JSON.stringify(f1.serializeArray()),
            //data: JSON.stringify({un: "admin"}),
            data: JSON.stringify(f1.serializeObject()),
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
            data: JSON.stringify(f2.serializeObject()),
            type: "POST",
            dataType: "JSON",
            contentType: 'application/json',
            success: function (data) {
                alert(data);
            }
        });
    });

    $("#bt_3").click(function () {
        var f3 = $("#form_3");
        $.get("/demo/check/" + f3.serializeObject().name, function (d) {
            alert(d);
        });
    });
});