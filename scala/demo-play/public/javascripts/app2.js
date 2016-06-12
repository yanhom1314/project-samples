webpackJsonp([1],[
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(1);
	var jQuery = $ = __webpack_require__(5);

	window.onload = function() {
	    console.log("Hello World2!");
	    console.log($("body").html());
	    $.get('/demo/persons',function(data){
	        $.each(data,function(i,d){
	            var name = $("<div>").addClass("name").text(d.name);
	            var age = $("<div>").addClass("age").text(d.age);
	            $("#persons").append($("<li>").append(name).append(age));
	        });
	    });
	};


/***/ }
]);