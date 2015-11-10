'use strict';

angular.module('angularJsclientApp').factory('modelFactory', function () {

    var getStatuses = function () {
        var tempArray = [
            {name: 'Back Log'},
            {name: 'To Do'},
            {name: 'In Progress'},
            {name: 'Review'},
            {name: 'Verified'},
            {name: 'Done'}
        ];
        return tempArray;
    };

    var getTypes = function () {
        var tempArray = [
            {name: 'Feature'},
            {name: 'Enhancement'},
            {name: 'Bug'},
            {name: 'Spike'}
        ];
        return tempArray;
    };

    var getStories = function () {
        var tempArray = [
            {
                id: 1,
                title: 'Story 00',
                description: 'Create initial version',
                criteria: 'pending',
                status: 'To Do',
                type: 'Feature',
                reporter: 'Lukas Ruebbelke',
                assignee: 'Brian Ford'
            },
            {
                id: 2,
                title: 'Story 01',
                description: 'check box',
                criteria: 'pending',
                status: 'In Progress',
                type: 'Feature',
                reporter: 'Lukas Ruebbelke',
                assignee: 'Brian Ford'
            },
            {
                id: 3,
                title: 'Story 02',
                description: 'make list sortable',
                criteria: 'pending',
                status: 'To Do',
                type: 'Feature',
                reporter: 'Lukas Ruebbelke',
                assignee: 'Brian Ford'

            },
            {
                id: 4,
                title: 'Story 03',
                description: 'refactor',
                criteria: 'pending',
                status: 'Back Log',
                type: 'Bug',
                reporter: 'Lukas Ruebbelke',
                assignee: 'Brian Ford'

            },
            {
                id: 5,
                title: 'Story 05',
                description: 'refactor once',
                criteria: 'pending',
                status: 'Done',
                type: 'Bug',
                reporter: 'Lukas Ruebbelke',
                assignee: 'Brian Ford'

            },
            {
                id: 6,
                title: 'Story 04',
                description: 'improve performance',
                criteria: 'pending',
                status: 'Review',
                type: 'Spike',
                reporter: 'Lukas Ruebbelke',
                assignee: 'Brian Ford'

            }
        ];

        return tempArray;
    };

    return {
        getStories: getStories,
        getStatuses: getStatuses,
        getTypes: getTypes
    };
});
