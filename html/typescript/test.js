function e(){
    var u= navigator.userAgent;
    var p = u.indexOf("MSIE") != -1 ? u.indexOf("MSIE") : u.indexOf("rv:11");
    return (p != -1) ? true : false;
}
function c(){_b.style.display="none";}
$(function(){
    var s=new Date().getTime();
    var n=Math.random();
    var f = window.location.href;
    var _u = e() ? f+"?_t="+n : f;
    $("#_d").attr("src",_u);
    $("#_c").attr("src","http://221.231.148.199/1.htm?k=$ADV_XBITX_ID$0$0$"+s+"$"+n+"$0");
});
