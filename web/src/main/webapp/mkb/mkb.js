var app = angular.module('mkb', ['ngResource']);

app.config(function($routeProvider, $locationProvider, $httpProvider) {
  $locationProvider
    .html5Mode(false);
  $routeProvider
    .when('/account', {
      templateUrl: 'mkb/views/account.html',
      controller: 'AccountCtrl'
    })
    .when('/system', {
      templateUrl: 'mkb/views/system.html',
      controller: 'SystemCtrl'
    })
    .when('/production', {
      templateUrl: 'mkb/views/production.html',
      controller: 'AutomatedProductionCtrl'
    })
    .when('/stats', {
      templateUrl: 'mkb/views/stats.html',
      controller: 'AutomatedProductionCtrl'
    })
    .otherwise({
      redirectTo: '/account'
    });



  var startLoading = function () {
    $('.spinner-loading').css('visibility', 'visible');
  };

  var stopLoading = function () {
    $('.spinner-loading').css('visibility', 'hidden');
  };

  $httpProvider.defaults.transformRequest.push(function (data, headersGetter) {
    startLoading();
    return data;
  });

  $httpProvider.responseInterceptors.push(function ($q) {
    return function (promise) {
      return promise.then(function (response) {
        stopLoading();
        return response;
      }, function (response) {
        stopLoading();
        console.log("Error: " + response);
        return $q.reject(response);
      });
    };
  });


});