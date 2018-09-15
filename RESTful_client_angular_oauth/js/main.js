angular.module('app', [])
    .controller('OrderInfo', function ($scope, $resource, $http, $httpParamSerializer, $cookies) {
        $scope.data = {
            grant_type: "password",
            username: "admin",
            password: "123456",
            client_id: "trusted_client"
        };
        $scope.encoded = btoa("clientIdPassword:secret");
        $scope.login = function () {
            var req = {
                method: 'POST',
                url: "http://localhost:8080/rest/oauth/token",
                headers: {
                    "Authorization": "Basic " + $scope.encoded,
                    "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
                },
                data: $httpParamSerializer($scope.data)
            };
            $http(req).then(function(data){
                $http.defaults.headers.common.Authorization =
                    'Bearer ' + data.data.access_token;
                $cookies.put("access_token", data.data.access_token);
                window.location.href="index";
            });
        };
        $http.get('http://localhost:8080/rest/api/v1/customers/18/orders/25')
            .then(function (response) {
                $scope.order = response.data;
            });
    });