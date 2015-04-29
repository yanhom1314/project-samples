var _ta = angular.module('myTestApp',[]).factory('time',function($timeout){
    var time={};
    (function tick(){
        time.now=new Date().toString();
        $timeout(tick,1000);
    })();
    return time;
});

_ta.controller("ClockCtrl", ['$scope', '$http', 'time', function ($scope, $http, time) {
    $scope.time=time;
}]);
