angular.module('app', [])
    .controller('OrderInfo', function ($scope, $http) {
        $http.get('http://localhost:8080/rest/v1/customers/1/orders/7')
            .then(function (response) {
                $scope.order = response.data;
            });
    });