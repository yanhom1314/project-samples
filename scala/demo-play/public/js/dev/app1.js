import $ from "jquery";
import {add} from "../lib/math";
import d7 from "../lib/config";

require("jquery-ui");
require("free-jqgrid-locale-cn");
require("free-jqgrid");

function resizeGrid() {
    var width = $(window).width() * 0.8;
    $("#list2").setGridWidth(width);
    console.log("width:" + width);
    return width;
}

$(function () {
    var csrfToken = $('meta[name="Csrf-Token"]').attr("content");
    console.log(add(12, 7));
    console.log(csrfToken);
    //jqgrid
    $("#list2").jqGrid({
        url: d7.context + 'jqgrid',
        datatype: "json",
        mtype: "POST",
        loadBeforeSend: function(jqXHR) {
            // you should modify the next line to get the CSRF tocken
            jqXHR.setRequestHeader('Csrf-Token', csrfToken);
        },
        colNames: ['id', 'firstName', 'lastName', 'address'],
        colModel: [
            {name: 'id', index: 'id', width: 90, align: "left"},
            {name: 'firstName', index: 'firstName', width: 90, align: "right"},
            {name: 'lastName', index: 'lastName', width: 100},
            {name: 'address', index: 'address', width: 150, sortable: false}
        ],
        width: resizeGrid(),
        rowNum: 10,
        rowList: [10, 20, 30],
        pager: '#pager2',
        sortname: 'id',
        viewrecords: true,
        sortorder: "desc",
        caption: "JSON Example"
    });
    $("#list2").jqGrid('navGrid', '#pager2', {edit: false, add: false, del: false});

    $(window).resize(resizeGrid);
});
