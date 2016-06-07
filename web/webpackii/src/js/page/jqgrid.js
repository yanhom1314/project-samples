require('css'); //加载初始化样式
var jQuery = $ = require("jquery");
var grid = require('grid').grid;
$(function () {
    //jqGrid
    grid('list2','pager2','data.json');
});
