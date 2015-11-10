'use strict';

angular.module('angularJsclientApp').factory('helferlein', function ($timeout) {

  var ALPHA = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';

  var b64Encode = function (number) {
    var result = '';
    if (number === 0) {
      return 'A';
    }
    while (number > 0) {
      var remainder = number % 64;
      number = Math.floor(number / 64);
      result = ALPHA.charAt(remainder) + result;
    }
    result.replace('/', '_');
    return result;
  };

  var id16bytes = function () {
    return b64Encode(Math.random() * 4294967296) +
      b64Encode(Math.random() * 4294967296) +
      b64Encode(Math.random() * 4294967296) +
      b64Encode(Math.random() * 4294967296);
  };

    var alertsArray = [];

    var buildIndex = function (source, property) {
        var tempArray = [];
        for (var i = 0, len = source.length; i < len; ++i) {
            tempArray[source[i][property]] = source [i];
        }
        return tempArray;
    };

    var closeAlert = function (index) {
        alertsArray.splice(index, 1);
    };

    var alert = function (type, msg, timeout) {

        var alert = {type: type, msg: msg};
        alertsArray.push(alert);

        if (timeout) {
            $timeout(function () {
                closeAlert(alertsArray.indexOf(alert));
            }, timeout);
        }
    };

    var warning = function (msg, timeout) {
        alert('warning', msg, timeout);
    };
    var danger = function (msg, timeout) {
        alert('danger', msg, timeout);
    };
    var info = function (msg, timeout) {
        alert('info', msg, timeout);
    };
    var success = function (msg, timeout) {
        alert('success', msg, timeout);
    };

    var alerts = function () {
        return alertsArray;
    };

    return {
        buildIndex: buildIndex,
        success: success,
        info: info,
        warning: warning,
        danger: danger,
        closeAlert: closeAlert,
      alerts: alerts,
      b64encode: b64Encode,
      id16bytes: id16bytes
    };

});
