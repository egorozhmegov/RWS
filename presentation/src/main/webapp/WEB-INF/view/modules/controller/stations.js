ctrlLayer.controller('stationCtrl', function($scope, $rootScope, $http, $cookieStore, $location) {

    var baseURL = "http://localhost:8080/view";

    $scope.init = function() {
        if($cookieStore.get("logedIn")){
            $rootScope.loggedIn = $cookieStore.get("logedIn");
            $rootScope.user = $cookieStore.get("user");
        } else {
            $location.path('/signin');
        }
    };

    $scope.init();

    $scope.getAllStations = function () {
        $http.get(baseURL + "/list_stations").then(function(response) {
            $scope.stations = response.data;
        });
    };

    $scope.addStation = function () {
        $http.post(baseURL + "/add_station", {
             "stationName":$scope.stationName
         }).then(function (response) {
                if(response.data.toString() == 'false'){
                    $scope.showAddStationError = true;
                } else {
                    $scope.showAddStationError = false;
                    $scope.getAllStations();
                    $scope.stationName = '';
                }
         });
    };

    $scope.getStation = function (station) {
        $scope.station = station;
    };

    $scope.editStation = function (station) {
        station.editing = true;
    };

    $scope.doneEditing = function (station) {
        station.editing = false;
        $scope.updateStation(station);
    };

    $scope.updateStation = function(station) {
        $http.put(baseURL + "/update_station", {
            "stationId":station.stationId,
            "stationName":station.stationName
        }).then(function () {
            $scope.getAllStations();
        })
    };

    $scope.removeStation = function(stationId){
        $http.delete(baseURL + "/remove_station/" + stationId)
            .then(function () {
                $scope.getAllStations();
            })
    };

});