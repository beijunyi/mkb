var app = angular.module('mkb', ['ngResource']);

app.config(function($routeProvider, $locationProvider) {
  $locationProvider
    .html5Mode(false);
  $routeProvider
    .when('/account', {
      templateUrl: 'mkb/views/account.html',
      controller: 'AccountCtrl'
    })
    .when('/manual', {
      templateUrl: 'mkb/views/manual.html',
      controller: 'ManualAccessCtrl'
    })
    .when('/auto', {
      templateUrl: 'mkb/views/auto.html',
      controller: 'AutomatedProductionCtrl'
    })
    .when('/stats', {
      templateUrl: 'mkb/views/stats.html',
      controller: 'AutomatedProductionCtrl'
    })
    .otherwise({
      redirectTo: '/account'
    })


});