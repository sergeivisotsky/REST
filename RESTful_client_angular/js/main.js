angular.module('app', [])
    .controller('OrderInfo', function ($scope, $http) {
        $http.get('http://localhost:8080/rest/api/v1/customers/8/orders/22')
            .then(function (response) {
                $scope.order = response.data;
            });
    });