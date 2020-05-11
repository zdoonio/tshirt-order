angular
    .module('newTshirtController', [
        'ui.router'
    ])
    .controller('newTshirt', newTshirt);

newTshirt.$inject = ['$scope', '$http'];

function newTshirt($scope, $http) {

    var vm = $scope;

    vm.tshirts = [];

    vm.submitForm = function (name, age, color, size) {
        vm.tshirts.push([name, age, color, size]);
    };

    vm.trysubmit = function () {
        vm.submitted = true;
    };

    vm.properName = function (value) {
        return /^[A-ZŁ]{1}[a-złśćżźąę]{1,}$/.test(value);
    };

    vm.improperSings = function (value) {
        return /[0-9\*\/\?\:\.\^\+\\\+\|!@#$%&()~.]/.test(value);
    };

    vm.empty = function (value) {
        return value === '';
    };

    vm.getStyle = function (color, size) {
        return {
            'background-color': this.convertColorName(color),
            'width': this.convertSize(size),
            'height': this.convertSize(size)
        };
    };

    vm.convertColorName = function (value) {
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

    vm.convertSize = function (value) {
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

    vm.checkAvaliability = function (data) {
        $http.post('http://localhost:9000/api/order/check ', data).then(function (result) {
            console.log(result);
            if (result.data.code === 400) {
                vm.message = result.data.message;
                vm.showErrorAlert = true;
                vm.tshirts = vm.tshirts.splice(-1,1);
                return false;
            } else {
                return true;
            }
        });
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
