angular.module( 'tshirtForm', [
    'ui.router'
])
.directive( 'tshirtForm', tshirtForm );

function tshirtForm() 
{
  return {
   restrict: 'E',
   templateUrl: 'http://localhost:9000/assets/front/partial/tshirtForm.html'
  };
}