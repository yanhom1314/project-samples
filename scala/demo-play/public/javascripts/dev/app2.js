var $ = jQuery = require('jquery');

require("bootstrap-css");
require("main-css");

require("datatables-css");
require("datatables");

window.onload = function () {
    console.log("Hello World2!");
    console.log($("body").html());
    $.get('/demo/persons', function (data) {
        $.each(data, function (i, d) {
            var name = $("<div>").addClass("name").text(d.name);
            var age = $("<div>").addClass("age").text(d.age);
            $("#persons").append($("<li>").append(name).append(age));
        });
    });

    //data table
    $('#example').DataTable({
        "ajax": '/demo/datatables',
        "columns": [
            {"data": "id"},
            {"data": "firstName"},
            {"data": "lastName"},
            {"data": "address"}
        ]
    });
};
