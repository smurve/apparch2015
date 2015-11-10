'use strict';

var angelloApp = angular.module('angularJsclientApp', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute',
  'ngGrid',
  'ui.bootstrap',
  'base64'
]);

angelloApp.config(function ($routeProvider) {
        $routeProvider
          .when('/stories', {
                templateUrl: 'views/main.html',
                controller: 'MainCtrl'
            }).when('/projects', {
                templateUrl: 'views/projects.html',
                controller: 'ProjectCtrl'
          }).when('/gents', {
            templateUrl: 'views/gents.html',
            controller: 'GentsCtrl'
            })
            .otherwise({
            redirectTo: '/gents'
            });
    });

