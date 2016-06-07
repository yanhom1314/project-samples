/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};

/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {

/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId])
/******/ 			return installedModules[moduleId].exports;

/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			exports: {},
/******/ 			id: moduleId,
/******/ 			loaded: false
/******/ 		};

/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);

/******/ 		// Flag the module as loaded
/******/ 		module.loaded = true;

/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}


/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;

/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;

/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";

/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(0);
/******/ })
/************************************************************************/
/******/ ([
/* 0 */
/***/ function(module, exports, __webpack_require__) {

	__webpack_require__(!(function webpackMissingModule() { var e = new Error("Cannot find module \"term.js\""); e.code = 'MODULE_NOT_FOUND'; throw e; }()));
	window.addEventListener('load', function() {
	  var socket = io.connect();
	  socket.on('connect', function() {
	    var term = new Terminal({
	      cols: 80,
	      rows: 24,
	      screenKeys: true
	    });
	 
	    term.on('data', function(data) {
	      socket.emit('data', data);
	    });
	 
	    term.on('title', function(title) {
	      document.title = title;
	    });
	 
	    term.open(document.body);
	 
	    term.write('\x1b[31mWelcome to term.js!\x1b[m\r\n');
	 
	    socket.on('data', function(data) {
	      term.write(data);
	    });
	 
	    socket.on('disconnect', function() {
	      term.destroy();
	    });
	  });
	}, false);


/***/ }
/******/ ]);