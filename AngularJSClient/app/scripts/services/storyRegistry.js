'use strict';

angular.module('angularJsclientApp').factory('storyRegistry', function ($http, configService, helferlein) {

    var stories = [];
    var index = {};
    var server = configService.getConfig('restServer');
    var timeoutShort = 1500;


    var successMsg = function (msg) {
        helferlein.success(msg, timeoutShort);
    };

    var loadStories = function (onSuccess) {
        stories = [];
        $http.get(server + 'rest/stories').success(function (retrieved) {
            for (var i in retrieved) {
                var story = retrieved[i];
                story.dirty = false;
                register(story);
            }
            if (onSuccess) {
                onSuccess();
            }

            successMsg('Loaded all stories');
        });
    };

    var register = function (story) {
        // provide a random id for new stories
        if (( !story.id ) || (story.id === 0 )) {
          story.id = helferlein.id16bytes();
            story.virgin = true;
        }
        stories.push(story);
        index[story.id] = story;
    };

    var removeStory = function (story) {
        var idx = stories.indexOf(story);
        if (idx > -1) {
            delete index[story.id];
            stories.splice(idx, 1);
            if (!story.virgin) { // don't delete stories that haven't made it to the server side yet
                $http.delete(server + 'rest/story/' + story.id).success(successMsg);
            }
        }
    };

    var cleanAndSuccessMsg = function (response) {
        index[response.id].dirty = false;
        successMsg(response.msg);
    };

    var saveDirties = function () {
        for (var idx in stories) {
            var story = stories[idx];
            if (story.dirty) {
                delete story.dirty;
                if (story.virgin) {
                    delete story.virgin;
                }
                $http.post(server + 'rest/story', story).success(cleanAndSuccessMsg);
            }
        }
    };


    var saveSingle = function (story) {
        if (story.dirty) {
            delete story.dirty;
            if (story.virgin) {
                delete story.virgin;
            }
            $http.post(server + 'rest/story', story).success(cleanAndSuccessMsg);
        }
    };

    var getStories = function () {
        return stories;
    };

    return {
        register: register,
        loadStories: loadStories,
        removeStory: removeStory,
        saveDirties: saveDirties,
        getStories: getStories,
        saveSingle: saveSingle
    };
});
