var AccountService = function ($resource) {
  return {
    login: function(username, password, refresh, callback) {
      return $resource('/api/account/login').get({
        username: username,
        password: password,
        refresh: refresh
      }, callback);
    },

    refreshUserInfo: function(username, callback) {
      return $resource('/api/account/refresh').get({
        username: username
      }, callback);
    },

    getFriends: function(username, refresh, callback) {
      return $resource('/api/account/friends').get({
        username: username,
        refresh: refresh
      }, callback);
    },

    getCards: function(username, refresh, callback) {
      return $resource('/api/account/cards').get({
        username: username,
        refresh: refresh
      }, callback);
    },

    getMazeStatus: function(username, callback) {
      return $resource('/api/account/mazestatus').get({
        username: username
      }, callback);
    },

    resetMaze: function(username, id, callback) {
      return $resource('/api/account/resetmaze').get({
        username: username,
        id: id
      }, callback);
    },

    clearMaze: function(username, id, max, callback) {
      return $resource('/api/account/clearmaze').get({
        username: username,
        id: id,
        max: max
      }, callback);
    }
  }
};

app.factory('AccountService', ['$resource', AccountService]);