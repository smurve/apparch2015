'use strict';

angular.module('angularJsclientApp').factory('okCancel', function ($modal) {

    var OkCancelController = function ($scope, $modalInstance, context) {

        $scope.ok = function () {
            $modalInstance.close('save');
        };
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        $scope.text = context.text;
        $scope.title = context.title;

    };

    var openModal = function (title, text, onOk, onCancel) {
        $modal.open({
            templateUrl: 'views/okCancel.html',
            controller: OkCancelController,
            resolve: { context: function () {
                return {
                    title: title,
                    text: text
                };
            }
            }}).result.then(function () {
                onOk();
            }, function () {
                onCancel();
            });
    };


    return {
        openModal: openModal
    };
});