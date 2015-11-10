'use strict';

angular.module('angularJsclientApp').factory('GentsService', function ($http, configService) {

  var metaData = {};

  var server = configService.getConfig('restServer');

  var allGents = [];

  var retrieveAllMetaData = function (onSuccess) {

    $http.get(server + 'rest/gents/metadata').success(function (retrieved) {
      metaData = retrieved;

      extractEntities();
      if (onSuccess) {

        onSuccess(retrieved);
      }

    });

  };

  var extractEntities = function () {
    metaData.entities.forEach(function (gent) {
      allGents.push(gent);
    });
    return allGents;
  };

  var findAll = function (gent, onSuccess) {
    $http.get(server + 'rest/gents/data/' + gent.entityName).success(function (retrieved) {
      metaData = retrieved;

      if (onSuccess) {

        onSuccess(retrieved);
      }

    });

  };

  return {
    retrieveAllMetaData: retrieveAllMetaData,
    allGents: allGents,
    findAll: findAll
  };


});
