'use strict';


angular.module('angularJsclientApp')
    .controller('MainCtrl', function ($scope, $modal, helferlein, modelFactory, okCancel, configService, storyRegistry) {


        $scope.statuses = modelFactory.getStatuses();
        $scope.types = modelFactory.getTypes();
        $scope.stories = function () {
            return storyRegistry.getStories();
        };
        $scope.typesIndex = helferlein.buildIndex($scope.types, 'name');
        $scope.statusesIndex = helferlein.buildIndex($scope.statuses, 'name');

        $scope.helferlein = helferlein;

        $scope.showSaveRemoveModal = false;

        $scope.styles = {};
        $scope.styles.Feature = 'type-feature';
        $scope.styles.Bug = 'type-bug';
        $scope.styles.Enhancement = 'type-enhancement';
        $scope.styles.Spike = 'type-spike';

        $scope.editStory = function (story) {
            $scope.setCurrentStory(story);
            $scope.setCurrentDirty();
            $scope.openSingleStoryModal();
        };

        $scope.viewStory = function (story) {
            $scope.setCurrentStory(story);
            $scope.openSingleStoryModal();
        };

        $scope.setCurrentStatus = function (status) {
            if (typeof $scope.currentStory !== 'undefined') {
                $scope.currentStory.status = status.name;
            }
        };

        $scope.setCurrentType = function (type) {
            if (typeof $scope.currentStory !== 'undefined') {
                $scope.currentStory.type = type.name;
            }
        };

        $scope.createStory = function () {
            var newStory = {status: 'Back Log', type: 'Feature', dirty: true};

            storyRegistry.register(newStory);
            $scope.setCurrentStory(newStory);
            $scope.openSingleStoryModal();
        };

        $scope.setCurrentStory = function (story) {
            $scope.currentStory = story;
            $scope.currentStatus = $scope.statusesIndex[story.status];
            $scope.currentType = $scope.typesIndex[story.type];
        };

        $scope.storyClass = function (story) {
            if (story === $scope.currentStory) {
                return 'rounded story selected-story ' + $scope.styles[story.type];
            } else {
                return 'rounded story ' + $scope.styles[story.type];
            }
        };

        $scope.clearCurrentStory = function () {
            delete $scope.currentStory;
            delete $scope.currentStatus;
            delete $scope.currentType;
        };

        var anyIsDirty = function () {
            var found = false;
            var stories = $scope.stories();
            stories.forEach(function (story) {
                if (story.dirty) {
                    found = true;
                }
            });
            return found;
        };

        $scope.safeLoadStories = function () {
            if (!anyIsDirty()) {
                $scope.loadStories();
            } else {
                okCancel.openModal('Reloading stories will discard all changes.',
                    'Do you really want to continue"?',
                    function () {
                        $scope.loadStories();
                    },
                    function () {
                        console.log('cancelled...');
                    });

            }
        };
        $scope.loadStories = function () {
            storyRegistry.loadStories();
            $scope.clearCurrentStory();
        };

        $scope.removeCurrent = function () {
            storyRegistry.removeStory($scope.currentStory);
            $scope.clearCurrentStory();
        };

        $scope.setCurrentDirty = function () {
            if ($scope.currentStory) {
                $scope.currentStory.dirty = true;
            }
        };

        $scope.saveDirties = function () {
            storyRegistry.saveDirties();
        };

        $scope.safeRemoveStory = function (story) {
            okCancel.openModal('Attention', 'Do you really want to remove story "' + story.title + '"?',
                function () {
                    $scope.removeStory(story);
                },
                function () {
                    console.log('cancelled...');
                });
        };

        $scope.removeStory = function (story) {
            $scope.setCurrentStory(story);
            $scope.removeCurrent();
        };


        var SingleStoryController = function ($scope, $modalInstance, context) {

            $scope.setCurrentStatus = function (status) {
                if (typeof $scope.currentInstance !== 'undefined') {
                    $scope.currentInstance.status = status.name;
                }
            };

            $scope.setCurrentType = function (type) {
                if (typeof $scope.currentInstance !== 'undefined') {
                    $scope.currentInstance.type = type.name;
                }
            };

            $scope.save = function () {
                $modalInstance.close('save');
            };
            $scope.close = function () {
                $modalInstance.dismiss('cancel');
            };
            $scope.setDirty = function () {
                $scope.currentInstance.dirty = true;
            };

            $scope.currentInstance = context.current;
            $scope.currentStatus = context.currentStatus;
            $scope.currentType = context.currentType;
            $scope.types = context.types;
            $scope.statuses = context.statuses;

        };

        $scope.openSingleStoryModal = function () {
            $modal.open({
                templateUrl: 'views/singleStory.html',
                controller: SingleStoryController,
                resolve: { context: function () {
                    return {
                        current: $scope.currentStory,
                        currentStatus: $scope.currentStatus,
                        currentType: $scope.currentType,
                        types: $scope.types,
                        statuses: $scope.statuses,
                        typesIndex: $scope.typesIndex,
                        statusesIndex: $scope.statusesIndex};
                }
                }}).result.then(function (msg) {
                    console.log(msg); // save
                    storyRegistry.saveSingle($scope.currentStory);
                }, function (msg) {
                    console.log(msg); // close
                });
        };

        storyRegistry.loadStories();
    });
