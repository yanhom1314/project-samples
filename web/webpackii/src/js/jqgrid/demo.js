var $ = require('jquery');

require('jquery-ui');
require('jquery-ui/themes/sunny/jquery-ui.min.css');
require('free-jqgrid');
require('free-jqgrid/js/i18n/grid.locale-cn.js');
require('free-jqgrid/css/ui.jqgrid.min.css');

module.exports = {
    grid: function (listId,pageId,url) {
        $("#"+listId).jqGrid({
            url: url,
            datatype: "json",
            colNames: ['Inv No', 'Date', 'Client', 'Amount', 'Tax', 'Total', 'Notes'],
            colModel: [
                { name: 'id', index: 'id', width: 55 },
                { name: 'invdate', index: 'invdate', width: 90 },
                { name: 'name', index: 'name asc, invdate', width: 100 },
                { name: 'amount', index: 'amount', width: 80, align: "right" },
                { name: 'tax', index: 'tax', width: 80, align: "right" },
                { name: 'total', index: 'total', width: 80, align: "right" },
                { name: 'note', index: 'note', width: 150, sortable: false }
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#pager2',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "JSON Example"
        });
        $("#"+listId).jqGrid('navGrid', '#'+pageId, { edit: false, add: false, del: false });
    }
};