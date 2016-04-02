(function() {

    var purchaserApp = angular.module('purchaser', []);

    var PurchaserListController = function($scope, $http) {

        $scope.purchasers = null;
        $scope.error = null;

        $scope.listPurchasers = function(id) {

            $scope.purchasers = null;
            $scope.error = null;

            var config = {
                headers: {
                    'Content-Type': 'application/hal+json',
                    'Accept': 'application/hal+json'
                }
            };

            $http.get('http://localhost:8080/purchaser', config)
                .then(onPurchasersComplete, onPurchasersError);
        }

        var onPurchasersComplete = function(response) {
            $scope.purchasers = response.data._embedded.purchaserResourceList;
        }

        var onPurchasersError = function(reason) {
            $scope.error = 'Could not fetch the data due to: \'' + reason + '\'';
        }
    }

    var PurchaserGetController = function($scope, $http) {

        $scope.purchaser = null;
        $scope.error = null;

        var onPurchaserComplete = function(response) {
            $scope.purchaser = response.data.purchaser;
        }

        var onPurchaserError = function(reason) {
            $scope.error = 'Could not fetch the data due to: ' + reason;
        }

        $scope.callPurchaserGet = function(id) {

            $scope.purchaser = null;
            $scope.error = null;

            var config = {
                headers: {
                    'Content-Type': 'application/hal+json',
                    'Accept': 'application/hal+json'
                }
            };
            window.alert('config: ' + config);

            $http.get('http://localhost:8080/purchaser/' + id, config)
                .then(onPurchaserComplete, onPurchaserError);
        }
    }

    purchaserApp.controller('PurchaserListController', ["$scope", "$http", PurchaserListController]);
    purchaserApp.controller('PurchaserGetController', ["$scope", "$http", PurchaserGetController]);

})();
