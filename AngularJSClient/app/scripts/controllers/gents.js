'use strict';

angular.module('angularJsclientApp')
  .controller('GentsCtrl', function ($scope, GentsService) {


    var exposeToScope = function (result) {
      $scope.allEntityNames = GentsService.allEntityNames;
      $scope.allGents = GentsService.allGents;
      $scope.colSpec = generateColumnSpec(result);
    };

    var generateColumnSpec = function (metadata) {
      var colspec = {};
      for (var idx in metadata.entities) {
        var entity = metadata.entities[idx];
        var spec = {};

        var temp = [];

        for (var attidx in entity.attributes) {
          var att = entity.attributes[attidx];
          spec.att = {caption: att.label.singular};
          temp[att.orderByIdx] = att.uniqueName;
        }
        colspec[entity.entityName] = spec;
        colspec.attributeNames = temp;
      }


      return colspec;

    };

    GentsService.retrieveAllMetaData(function (result) {
      exposeToScope(result);
    });

    $scope.setCurrentGent = function (gent) {
      $scope.selectedGent = gent;

      GentsService.findAll(gent, function (result) {
        var gridOptions = {data: result};
        $scope.gridOptions = gridOptions;
        prepareResultSet(result);
      });

    };

    var prepareResultSet = function (entities) {
      var data = [];

      for (var rowIdx in entities) {
        var entity = entities[rowIdx];
        var row = [];
        for (var elm in entity) {
          row.push({col: elm, value: entity[elm]});
        }
        data.push(row);
      }

      $scope.currentDataset = data;
    };


  });
