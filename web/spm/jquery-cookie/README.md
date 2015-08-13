# jquery-cookie [![spm version](http://spmjs.io/badge/jquery-cookie)](http://spmjs.io/package/jquery-cookie)

---

封装[jQuery-cookie](https://github.com/carhartl/jquery-cookie/)。

---

## Install

```
$ spm install jquery-cookie --save
```

## Usage

````js
var $ = require('jquery');
require('jquery-cookie')($);
//use
$.cookie("S_COOKIE","123321");
console.log($.cookie("S_COOKIE"));
````
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
              'jquery-cookie': 'jquery-cookie/1.4.1/jquery.cookie-debug'
            }
        });
    seajs.use(['jquery','jquery-cookie'],function($,cookie) {
      cookie($);
      $.cookie('s_set',"123321323");
      console.log($.cookie('s_set'));
      $("#_c").html($.cookie('s_set'));
    });
    //-->
  </script>
</head>
<body>
  <span id="_c" style="color:gray"></span>
</body>
</html>
````

## 修改
+ 将`jquery.cookie.js`封装:
```js
    module.exports = function(jQuery) {
        @jquery.cookie.js  
    };
```
+ 修改`jquery.cookie.js`对`CommonJS`支持的部分：`factory(require('jquery'));`修改为`factory(jQuery);`；
