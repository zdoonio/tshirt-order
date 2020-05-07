var app = angular.module('orderer', [
    'ui.router',
    'ordersController', 
    'newTshirtController',
    'sendController',
    'tshirtForm',
	'orderSend',
    'siteNav'
]);

app.config(['$stateProvider', function($stateProvider) {
    $stateProvider
    .state( 'orders', {
        url: '/orders',
        templateUrl: 'http://localhost:9000/assets/front/partial/orderList.html',
        controller: 'orders'
    })
    .state( 'addOrder', {
        url: '/add/orders',
        templateUrl: 'http://localhost:9000/assets/front/partial/orderAdd.html',
        controller: 'newTshirt'
    });
}]);

app.config(['$qProvider', function ($qProvider) {
    $qProvider.errorOnUnhandledRejections(false);
}]);