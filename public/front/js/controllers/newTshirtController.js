angular
	.module( 'newTshirtController', [
		'ui.router'
	])
	.controller( 'newTshirt', newTshirt );

	newTshirt.$inject = [ '$scope', '$http' ];

function newTshirt( $scope, $http ) {

	var vm = $scope;

	vm.tshirts = [];
	
	vm.submitForm = function(name, age, color, size){
		vm.tshirts.push([name, age, color, size]);
	};

	vm.trysubmit = function() {
		vm.submitted = true;
	};

	vm.properName = function( value ) {
		return /^[A-ZŁ]{1}[a-złśćżźąę]{1,}$/.test(value);
	};

	vm.improperSings = function( value ) {
		return /[0-9\*\/\?\:\.\^\+\\\+\|!@#$%&()~.]/.test( value );
	};

	vm.empty = function(value) {
		return value==='';
	};

	vm.colourOptions = [
		{
			value: 'czerwony',
			name: 'czerwony'
		},
		{
			value: 'niebieski',
			name: 'niebieski'
        },
        {
			value: 'zielony',
			name: 'zielony'
        }
	];
}
