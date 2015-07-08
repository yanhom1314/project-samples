var _n2a = angular.module('n2aApp',[]).factory('time',function($timeout){
    var time={};
    (function tick(){
        time.now=new Date().toString();
        $timeout(tick,1000);
    })();
    return time;
});

_n2a.controller("TranslateCtrl", ['$scope', '$http', 'time', function ($scope, $http, time) {
    $scope.time=time;

    $scope.native2ascii= function (data) {
        var regexp = /[^\x00-\xff]/g;
        var a = data.native;
        var m = null;
        while (m = regexp.exec(data.native)) {
            a = a.split(m[0]).join(escape(m[0]).split("%").join("\\"));
        }
        data.ascii= a.toLowerCase();
    };

    $scope.ascii2native = function (data) {
        data.native = unescape(data.ascii.split("\\").join("%")).toLowerCase();
    };
    $scope.clear = function() {
        $scope.data = {};
    };
}]);
