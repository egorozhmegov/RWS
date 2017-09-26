ctrlLayer.controller('trainCtrl', function($scope, $rootScope, $http, $cookieStore, $location) {

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

    $scope.getAllTrains = function () {
        $http.get(baseURL + "/list_trains").then(function(response) {
            $scope.trains = response.data;
        });
    };

    $scope.addTrain = function () {
        $http.post(baseURL + "/add_train", {
            "trainNumber":$scope.trainNumber,
            "tariff":$scope.tariff
        }).then(function (response) {
            if(response.data.toString() == 'false'){
                $scope.showAddTrainError = true;
            } else {
                $scope.showAddTrainError = false;
                $scope.getAllTrains();
                $scope.trainNumber = '';
                $scope.tariff = '';
            }
        });
    };

    $scope.getTrain = function (train) {
        $scope.train = train;
    };

    $scope.editTrain = function (train) {
        train.editing = true;
    };

    $scope.doneEditing = function (train) {
        train.editing = false;
        $scope.updateTrain(train);
    };

    $scope.updateTrain = function(train) {
        $http.put(baseURL + "/update_train", {
            "trainId":train.trainId,
            "trainNumber":train.trainNumber,
            "tariff":train.tariff
        }).then(function () {
            $scope.getAllTrains();
        })
    };

    $scope.removeTrain = function(trainId){
        $http.delete(baseURL + "/remove_train/" + trainId)
            .then(function () {
                $scope.getAllTrains();
            })
    };

    $scope.stations = $http.get(baseURL + "/list_stations").then(function(response) {
        $scope.stations = response.data;
    });

    $scope.setSearchQuery = function(query) {
        $scope.addPoint = query;
        $scope.focus = false;
    };

    $scope.addRootPoint = function () {
        if($scope.train == null){
            $scope.showAddRootPointError = true;
        } else {
            $scope.rootpoint = [$scope.addPoint, $scope.arrivalTime, $scope.departureTime, $scope.train.trainId];
            $http.post(baseURL + "/add_rootpoint", $scope.rootpoint).then(function (response) {
                if(response.data.toString() == 'false'){
                    $scope.showAddRootPointError = true;
                } else {
                    $scope.showAddRootPointError = false;
                    $scope.addPoint = '';
                    $scope.arrivalTime = '';
                    $scope.departureTime = '';
                }
            });
        }
    };
    
    $scope.arrivalTime =  function (trainNumber) {
        alert(trainNumber);
    };
    
});




