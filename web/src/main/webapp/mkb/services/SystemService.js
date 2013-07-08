var SystemService = function ($resource) {
  return {
    findAccounts: function (filters, callback) {
      return $resource('/api/system/accounts').save({
        filters: filters
      }, callback);
    },

    getBossPool: function(callback) {
      return $resource('/api/system/boss').get({}, callback);
    },

    getMapPool: function(callback) {
      return $resource('/api/system/map').get({}, callback);
    },

    getMazePool: function(callback) {
      return $resource('/api/system/maze').get({}, callback);
    },

    getFenergyPool: function(callback) {
      return $resource('/api/system/fenergy').get({}, callback);
    },

    getFriendsPool: function(callback) {
      return $resource('/api/system/friends').get({}, callback);
    },

    getLegionPool: function(callback) {
      return $resource('/api/system/legion').get({}, callback);
    }
  }
};

app.factory('SystemService', ['$resource', SystemService]);