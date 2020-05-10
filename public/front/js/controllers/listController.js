var controllerOrders = angular.module('ordersController', ['ui.router']);

controllerOrders.controller('orders', ['$scope', '$http', '$filter', function($scope, $http){
    
    $http.get('http://localhost:9000/api/orders').then(function(data){
		$scope.orders = data.data.result;
	});

	$scope.refreshData = function() {

	};

	$scope.getStyle = function(color, size){
		return {'background-color' : this.convertColorName(color),
			'width' : this.convertSize(size),
			'height' : this.convertSize(size)};
	};

	$scope.convertColorName = function(value) {
		switch (value) {
			case 'czerwony': {
				return 'red';
			}
			case 'niebieski': {
				return 'blue';
			}
			case 'zielony': {
				return 'green';
			}
			default: {
				return 'black';
			}
		}
	};

	$scope.convertSize = function(value) {
		switch (value) {
			case 'S': {
				return '25px';
			}
			case 'M': {
				return '50px';
			}
			case 'L': {
				return '75px';
			}
			case 'XL': {
				return '100px';
			}
			default: {
				return '0px';
			}
		}
	};
}]);