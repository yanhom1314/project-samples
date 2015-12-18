# React演示

## 使用NPM
* 环境：
  npm install gulp -g
  npm install gulp --save-dev
  npm install gulp-minify-css gulp-concat gulp-uglify gulp-rename gulp-react gulp-babel babel-preset-react del --save-dev
* main.js
[source,javascript]
----
// main.js
var React = require('react');
var ReactDOM = require('react-dom');

ReactDOM.render(
	<h1>Hello, world!</h1>,
  	document.getElementById('example')
);
----
* build
  npm install --save react react-dom babelify babel-preset-react
  npm install -g browserify
  browserify -t [ babelify --presets [ react ] ] main.js -o bundle.js

* hello.html
[sorce,html]
-----
<!DOCTYPE html>
<html>
  <head>
	<script type="text/javascript" src="//cdn.bootcss.com/react/0.14.3/react.min.js"></script>
	<script type="text/javascript" src="//cdn.bootcss.com/react/0.14.3/react-dom.min.js"></script>
  </head>
  <body>
	<div id='example'></div>
	<script src="bundle.js"></script>
  </body>  
</html>
-----


## gulp-react已经被gulp-babel取代，没有测试！！！
