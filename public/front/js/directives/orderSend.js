angular.module( 'orderSend', [
    'ui.router'
])
.directive( 'orderSend', orderSend );

function orderSend()
{
  return {
   restrict: 'E',
   templateUrl: 'http://localhost:9000/assets/front/partial/orderSend.html'
  };
}