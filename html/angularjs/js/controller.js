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
