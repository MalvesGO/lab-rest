angular.module('alvoApp').controller('EmailController',	function($scope, $routeParams, $location, growl, dialogs, recursoEmail) {
	$scope.email = '';
	$scope.usuarioRegistro = {
			email : undefined,
			login : '',
			senha : '',
			nome : '',
		};

	$scope.registrarEmail = function() {
		recursoEmail.registrarEmail($scope.email)
		.then( function(resp) {
			console.log(resp);
			$location.path("/home");
			dialogs.notify('Aviso', 'Um email foi enviado para: <br>' +
						  $scope.email + ' <br>'+
						  'Para dar continuidade ao registro, <br> ' +
						  'siga as instruções no email enviado.', {'size':'sm'});
		}).catch(function(erro) {
			console.log(erro);
			growl.error(erro.mensagem, {title: 'Atenção!'});
		});
	};

	$scope.verificarIdRegistro = function() {
		$scope.usuarioRegistro.email = undefined;
		if ($routeParams.registroId) {
			//console.log('routeParams.registroId: '+$routeParams.registroId);
			recursoEmail.verificarRegistroId($routeParams.registroId)
			.then(function(resp){
				var entidade = angular.fromJson(resp.entidade);
				//console.log('EmailController.verificarIdRegistro.resp.entidade: '+entidade.email);
				$scope.usuarioRegistro.email = resp.entidade.email;
				//console.log('$scope.usuarioRegistro.email '+$scope.usuarioRegistro.email);
			}).catch(function(erro) {
				console.log(erro);
				growl.error(erro.mensagem, {title: 'Atenção!'});
			});
		} else {
			growl.error('Codigo de confirmação de registro inválido ou não informado!', {title: 'Atenção!'});
		}
	};

	$scope.submeter = function() {
		recursoEmail.cadastrarUsuario($scope.usuarioRegistro)
		.then(function(resp) {
			$location.path("/login");
			growl.success('Usuario cadastrado com sucesso!');
		})
		.catch(function(erro) {
			growl.error(erro.mensagem, {title: 'Atenção!'});
		});
	};

})