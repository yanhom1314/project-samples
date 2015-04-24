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
    $scope.message = {loading: false};
    $scope.show = function (info) {
        $scope.message.loading = true;
        setTimeout(function () {
            $http.get('/js/content.json' + info.key).success(function (data) {
                $scope.data = data;
                $scope.message = {loading: false};
            });
        }, 2000);
    };
}]);
