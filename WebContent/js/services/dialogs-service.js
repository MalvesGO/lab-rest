angular.module('dialogsService', [])
	.factory('cDialogs', function($q, $mdDialog) {
		var recurso = {};

		recurso.loading = function() {
			//console.log('dialog.loading');
			$mdDialog.show({
				template: '<md-dialog class="loading-dialog" ng-cloak><md-progress-circular md-mode="indeterminate" md-diameter="100px"></md-progress-circular></md-dialog>',
				parent: angular.element(document.body),
				clickOutsideToClose:false,
				escapeToClose:false,
				ariaLabel:'Carregando...'
			});
		}

		recurso.inform = function(titulo, corpoHtml, textoBotao) {
			//console.log('dialog.inform');
			$mdDialog.show(
				 $mdDialog.alert()
				.title(titulo)
				.ariaLabel(titulo)
				.htmlContent(corpoHtml)
				.parent(angular.element(document.querySelector('#popupContainer')))
				.clickOutsideToClose(true)
				.ok(textoBotao)	 
			);
		}

		recurso.confirm = function(titulo, corppoHtml, textoSim, textoNao) {
			//console.log('dialog.confirm');
			return $q(function(resolve, reject) {
				var confirm = $mdDialog.confirm()
					.title(titulo)
					.ariaLabel(titulo)
					.htmlContent(corppoHtml)
					.ok(textoSim)
					.cancel(textoNao);
				$mdDialog.show(confirm).then(function() {
					resolve(textoSim);
				}, function() {
					reject(textoNao);
				});
			});
		}

		recurso.custom = function(templateUrl, locals) {
			//console.log('dialog.custom.entidade: '+angular.toJson(locals));
			return $q(function(resolve, reject) {
				//console.log('q.locals: '+locals)
				$mdDialog.show({
					controller: DialogController,
					controllerAs: 'ctrl',
					templateUrl: templateUrl,
					parent: angular.element(document.body),
					clickOutsideToClose: true,
					locals: locals,
				}).then(function(resp) {
					resolve(resp);
				}, function(error) {
					if (error) {
						console.log(error);
					}
					reject(error);
				});
			});
			function DialogController($scope, $mdDialog, locals) {
				//console.log('DialogController.locals: '+angular.toJson(locals));
				$scope.locals = locals;
			  $scope.hide = function() {
			    $mdDialog.hide();
			  };
			  $scope.cancel = function() {
			    $mdDialog.cancel();
			  };
			  $scope.answer = function(answer) {
			    $mdDialog.hide(answer);
			  };
			}
		}

		recurso.hide = function() {
			//console.log('dialog.hide')
			$mdDialog.hide();
		}

		return recurso;
	});