ctrlLayer.controller('indexCtrl', function($scope, $rootScope, $http, $cookieStore, $location) {
    $scope.logout = function () {
        $http.get('/j_spring_security_logout')
            .then(function () {
                $cookieStore.remove("logedIn");
                $cookieStore.remove("user");
                location.reload();
                $location.path('/signin');
            });
    };
});