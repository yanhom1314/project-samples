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

	var ha = angular.module("helloApp", []);

	ha.controller("HelloCtrl", ['$scope', '$http', function($scope, $http) {
		/*
		   $scope.phones = [
		   {name: "Nexus S", snippet: "Fast just got faster with Nexus S."},
		   {name: "Motorola XOOM? with Wi-Fi", snippet: "The Next, Next Generation tablet."},
		   {name: "MOTOROLA XOOM?", snippet: "The Next, Next Generation tablet."}
		   ];
		   */
		$http.get("/js/data.json").success(function(data, status, headers, config){
			console.log(data);
			$scope.phones = data;
		});
	}]);
	ha.controller("SayHello", ['$scope', '$http', function($scope, $http){
		$scope.name="Default the name!";
	}]);

	//Hello
	ha.controller("Hello", ['$scope', '$http', function ($scope, $http) {
		$scope.message = {loading: false};
		$scope.show = function (info) {
			$scope.message.loading = true;
			setTimeout(function () {
				$http.get('/js/form.json' + info.key).success(function (data) {
					$scope.info = data;
					$scope.message = {loading: false};
				});
			}, 2000);
		};

		$scope.reset = function () {
			$scope.info = angular.copy({});
		};

		//init
		$scope.saveSession = function () {
			if ($scope._form.$valid) {
				alert("Saving");
			}
		};
		$scope.reset();
	}]);

	//Sync
	ha.controller("Sync", ['$scope', '$http', function ($scope, $http) {
		$http.get('/js/content.json').success(function (data) {
			$scope.data = data;
		});
	}]);


/***/ }
/******/ ]);