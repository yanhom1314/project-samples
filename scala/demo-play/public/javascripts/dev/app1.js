var $ = jQuery = require("jQuery");

//css can use cdn(recommend)
//require("bootstrap-css");
//require("jquery-ui-css");
//require("free-jqgrid-css");
//require("main-css");

require("jquery-ui");
require("free-jqgrid-locale-cn");
require("free-jqgrid");

var add = require('math').add;

function resizeGrid() {
    var width = $(window).width() * 0.8;
    $("#list2").setGridWidth(width);
    console.log("width:" + width);
    return width;
}

$(function () {
    console.log(add(12, 7));
    //jqgrid
    jQuery("#list2").jqGrid({
        url: '/demo/jqgrid',
        datatype: "json",
        mtype: "POST",
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
    jQuery("#list2").jqGrid('navGrid', '#pager2', {edit: false, add: false, del: false});

    $(window).resize(resizeGrid);
});
