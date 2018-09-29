angular.module('app', [])
    .controller('CustomerInfo', function ($scope, $http) {
        $http.get('http://localhost:8080/rest/api/v1/customers/1')
            .then(function (response) {
                $scope.customer = response.data;
            });
    });