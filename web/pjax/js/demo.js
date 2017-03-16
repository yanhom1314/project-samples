var $ = jQuery = require("jQuery");
require("jquery-pjax");

function active(menu) {
  menu.addClass('menu-active');
  menu.attr("disabled","disabled"); 
}

function on(){
  
}

$(function () {  
    $(document).pjax('a', '#container');
    $('#clickMe').click(function () {
      $(this).addClass('menu-active');
      $(this).attr("disabled","disabled"); 
      $.pjax({
        url: '3.html',
        container: '#container'
      });
    });  
});