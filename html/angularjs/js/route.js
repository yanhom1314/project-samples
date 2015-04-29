
var _rApp = angular.module('routeApp',['ngRoute','_rControllers']);

_rApp.config(['$routeProvider',function($routeProvider){
    $routeProvider.when('/phones',{
        templateUrl:'/part/list.html',
        controller:'ListCtrl'
    }).when('/phones/:phoneId',{
        templateUrl:'/part/detail.html',
        controller:'DetailCtrl'
    })
}]);


var _rControllers=angular.module('_rControllers',[]);

_rControllers.controller('ListCtrl',['$http','$scope',function($http,$scope) {
    $http.get('/js/phones.json').success(function(data){
        $scope.phones = data;
    });
    $scope.orderProp="age";
}]);

_rControllers.controller('DetailCtrl', ['$scope', '$routeParams',
  function($scope, $routeParams) {
    $scope.phoneId = $routeParams.phoneId;
 }]);
