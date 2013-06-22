var AccountService = function ($resource) {
  return {
    login: function(username, password, refresh, callback) {
      return $resource('/api/account/login').get({
        username: username,
        password: password,
        refresh: refresh
      }, callback);
    },
    getFriends: function(username, refresh, callback) {
      return $resource('/api/account/friends').query({
        username: username,
        refresh: refresh
      }, {}, callback);
    },
    getCards: function(username, refresh, callback) {
      return $resource('/api/account/cards').query({
        username: username,
        refresh: refresh
      }, {}, callback);
    }
  }
};

app.factory('AccountService', ['$resource', AccountService]);