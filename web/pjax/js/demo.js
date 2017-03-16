var $ = jQuery = require("jQuery");
require("jquery-pjax");

$(function () {  
    $(document).pjax('a', '#container');
    $('#clickMe').click(function () {
      $.pjax({
        url: '3.html',
        container: '#container'
      });
    });  
});