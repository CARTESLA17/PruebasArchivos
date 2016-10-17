var app = angular.module('prueba', [ "ngResource" ]);

app.controller('Controlador1', [ '$scope', '$http',
                                     
	function($scope, $http) {	
	
		
	
		$scope.getVehiculos = function() {
			$http.get('rest/controlador/todos').success(function(data) {
				$scope.vehiculos = data;
			});
		}
		
		$scope.buscarCiudades = function() {
			$http.get('rest/controlador/ciudades').success(function(data) {
				$scope.ciudades = data;
			});
		}
		
		$scope.buscarMarcas = function() {
			$http.get('rest/controlador/marcas').success(function(data) {
				$scope.marcas = data;
			});
		}
		
		$scope.buscarModelos = function() {
			$http.get('rest/controlador/modelos').success(function(data) {
				$scope.modelos = data;
			});
		}
		
		$scope.addVehiculo = function() {
			$http.post('rest/controlador/addVehiculo', 
				{
					marcaId : $scope.marca.id,
					modeloId: $scope.modelo.id,
					ciudadId: $scope.ciudad.id,
					puertas : $scope.puertas,
					placa: $scope.placa
				}			
			).success(function(data) {
				if(data==1){					
					alert("El vehiculo se almaceno correctamente.");
				}else if(data==2){
					alert("El vehiculo NO se almaceno correctamente.");
				}else if(data==3){
					alert("Datos incompletos o incorrectos!.");
				}else if(data==4){
					alert("La placa ya existe!.");
				}else{
					alert("Datos incompletos o incorrectos!.");
				}				
			}).error(function(data) {
				$scope.msgBusUP = 'ERROR';
			});
		}
		
		$scope.update = function() {
			$http.post('rest/controlador/modelosPorMarca', 
					{
						marcaId : $scope.marca.id						
					}			
				).success(function(data) {
					$scope.modelos = data;			
				}).error(function(data) {
					$scope.msgBusUP = 'ERROR';
				});
			   
		}
	
		
} ]);