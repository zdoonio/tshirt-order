angular
	.module( 'sendController', [
		'ui.router'
	])
	.controller( 'sendController', sendOrder );

	sendOrder.$inject = [ '$scope', '$http' ];

function sendOrder( $scope, $http ) {

	var vm = $scope;

	vm.trySend = function(data) {
		var jsonList = [];
		for (var i = 0; i < data.length; i++)
		{
			var row = '{ "name" : "'+ data[i][0] +
					'", "age" : '+ data[i][1] +
					', "color" : "'+ data[i][2] +
					'", "size" : "'+ data[i][3] +
					'" }';
			var rowJson = JSON.parse(row);
			jsonList.push(rowJson);
		}
		console.log(jsonList);
		$http.post('http://localhost:9000/api/order', jsonList)
		.then( function ( data, status, headers, config ) {
			if(data.status === 201)
			vm.showAddAlert = true;
		}).catch(function (data) {
			console.log(data);
			vm.message = data.data.error;
			vm.showErrorAlert = true;
		});
	};
}
