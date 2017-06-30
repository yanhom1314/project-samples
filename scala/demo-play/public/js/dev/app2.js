var $ = jQuery = require('jQuery');

require("datatables-css");

require("datatables");

$(function () {
    $.get('/demo/persons', function (data) {
        $.each(data, function (i, d) {
            var name = $("<div>").addClass("name").text(d.name);
            var age = $("<div>").addClass("age").text(d.age);
            $("#persons").append($("<li>").append(name).append(age));
        });
    });

    //data table
    $('#example').DataTable({
        "language": {
            "decimal": "",
            "emptyTable": "没有数据",
            "info": "显示 _START_ 到 _END_ 共 _TOTAL_ 条记录",
            "infoEmpty": "显示 0 到 0 共 0 条记录",
            "infoFiltered": "(filtered from _MAX_ total entries)",
            "infoPostFix": "",
            "thousands": ",",
            "lengthMenu": "显示 _MENU_ 记录",
            "loadingRecords": "载入中...",
            "processing": "处理中...",
            "search": "查询:",
            "zeroRecords": "没发现记录",
            "paginate": {
                "first": "首页",
                "last": "末页",
                "next": "下一页",
                "previous": "上一页"
            },
            "aria": {
                "sortAscending": ": 列升序排序",
                "sortDescending": ": 列降序排序"
            }
        },
        "processing": true,
        "serverSide": true,
        "ajax": {
            "url": '/demo/datatables',
            "type": "GET"
        },
        "columns": [
            {"data": "id"},
            {"data": "firstName"},
            {"data": "lastName"},
            {"data": "address"}
        ]
    });
});
