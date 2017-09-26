ctrlLayer.controller('authenticateCtrl', function($scope, $rootScope, $http, $cookieStore, $location, registerDataSaveService){
    var baseURL = "http://localhost:8080/view";

    $scope.init = function() {
        if($cookieStore.get("logedIn")){
            $rootScope.loggedIn = $cookieStore.get("logedIn");
            $rootScope.user = $cookieStore.get("user");
            $location.path('/trains');
        }
    };

    $scope.init();


    $scope.login = function () {
        var dataL = "username="+$scope.username+"&password="+$scope.password+"&submit=Login";

        $http({
            method: 'POST',
            url: 'http://localhost:8080/j_spring_security_check',
            data: dataL,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
            .then(function(){
                $http.get(baseURL + "/authinfo")
                    .then(function (response) {
                        if(response.data.toString() == 'false'){
                            $scope.showSigninError = true;
                        } else {
                            $cookieStore.put("logedIn", true);

                            $http.get(baseURL + "/getLogin")
                                .then(function (response) {
                                    $cookieStore.put("user", response.data);
                                });
                            location.reload();
                            $location.path('/trains');
                        }
                    });
            });
    };

    

    $scope.comparePswd = function(){
        $scope.myPassword === $scope.confirm && $scope.confirm.length > 3 ?
            $scope.registration.confirm.$setValidity('required', true)
            : $scope.registration.confirm.$setValidity('required', false);
    };

    $scope.registerEmployee = function(){
        var registerData = {
            "employeeFirstName":$scope.firstName,
            "employeeLastName":$scope.lastName,
            "employeeEmail":$scope.myEmail,
            "employeeLogin":$scope.login,
            "employeePassword":$scope.myPassword
        };

        $http.post(baseURL + "/registerEmployee", registerData)
            .then(function(response){
                if(response.data.toString() == 'false'){
                    $scope.showRegisterError = true;
                } else {
                    registerDataSaveService.set(registerData);
                    $location.path('/successregister');
                }
            });
    };

    $scope.registerData = registerDataSaveService.get();
});