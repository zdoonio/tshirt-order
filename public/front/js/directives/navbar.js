angular.module( 'siteNav', [
    'ui.router'
])
.directive( 'siteNav', siteNav );

function siteNav() 
{
  return {
    restrict: 'E',
    templateUrl: 'http://localhost:9000/assets/front/navbar.html'
  };
}