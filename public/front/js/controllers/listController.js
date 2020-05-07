var controllerOrders = angular.module('ordersController', ['ui.router']);

controllerOrders.controller('orders', ['$scope', '$http', '$filter', function($scope, $http, $filter){
    
    $http.get('http://localhost:9000/api/orders').then(function(data){
    	console.log(data.data.result)
		$scope.orders = data.data.result;
	});

	$scope.refreshData = function() {

	};
}]);