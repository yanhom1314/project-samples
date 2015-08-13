# Demo

---

## Normal usage
````html
<!doctype html>
<html>
<head>
  <title> ztree demo </title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <script src="http://cdn.staticfile.org/seajs/2.3.0/sea.js"></script>
    <script type="text/javascript">
    <!--
        seajs.config({
            base: '/dist',
            alias: {
              'jquery': 'jquery/2.1.1/jquery-debug',
              'cookie': 'jquery-cookie/1.4.1/jquery.cookie-debug'
            },
            preload:['jquery']
        });
    seajs.use(['jquery','cookie'],function($,_) {
      $.cookie('s_set',"wahahahahaah");
      console.log($.cookie('s_set'));
    });
    //-->
  </script>

</head>
<body>
</body>
</html>
````
````javascript
seajs.use('index', function(jqueryCookie) {

});
````
