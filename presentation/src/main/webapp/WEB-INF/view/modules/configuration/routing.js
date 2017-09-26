configLayer.config(["$routeProvider", function($routeProvider){
    $routeProvider
        .when('/', {
            templateUrl: 'trains.html',
            controller: 'trainCtrl'
        })
        .when('/trains', {
            templateUrl: 'trains.html',
            controller: 'trainCtrl'
        })
        .when('/stations', {
            templateUrl: 'stations.html',
            controller: 'stationCtrl'
        })
        .when('/registration', {
            templateUrl: 'registration.html',
            controller: 'authenticateCtrl'
        })
        .when('/signin', {
            templateUrl: 'signin.html',
            controller: 'authenticateCtrl'
        })
        .when('/successregister', {
            templateUrl: 'successRegister.html',
            controller: 'authenticateCtrl'
        })
        .otherwise({
            redirectTo: '/trains'
        });
}]);
