angular.module('alvoApp', ['ngRoute', 'ngResource', 'alvoRotas', 'authInterceptor', 'headerDirective', 'loginService', 'usuarioService', 'categoriaService'])
	.config(function($httpProvider, $locationProvider) {
		//$locationProvider.html5Mode(true);
		//$httpProvider.interceptors.push('AuthInterceptor');
	}
);