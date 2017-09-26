var app = angular.module('app', ['controller']);

var ctrlLayer = angular.module('controller', ['service', 'ngAnimate', 'ngCookies']);

var serviceLayer = angular.module('service', ['configuration']);

var configLayer = angular.module('configuration', ['ngRoute']);

