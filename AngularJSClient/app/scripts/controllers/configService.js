'use strict';

angular.module('angularJsclientApp').factory('configService', function () {

    var configValues = {};

    configValues.restServer = 'http://localhost:8080/';

    var getConfig = function (configName) {
        return configValues[configName];
    };

    return {getConfig: getConfig };
});
