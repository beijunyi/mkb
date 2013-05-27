var app = angular.module('mkb', ['ngResource']);

app.config(function($routeProvider) {
  $routeProvider
    .when('/manual', {
      title: 'Manual Access',
      templateUrl: 'mkb/views/manual.html',
      controller: 'ManualAccessCtrl'
    })
    .when('/auto', {
      title: 'Automated Production',
      templateUrl: 'mkb/views/auto.html',
      controller: 'AutomatedProductionCtrl'
    })
    .when('/stats', {
      title: 'Statistics',
      templateUrl: 'mkb/views/stats.html',
      controller: 'AutomatedProductionCtrl'
    })
    .otherwise({
      redirectTo: '/manual'
    })
});