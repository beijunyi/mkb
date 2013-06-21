var AccountService = function ($resource) {
  return {
    login: function(username, password, callback) {
      return $resource('/api/account/login').get({
        username: username,
        password: password
      }, callback);
    }
  }
};

app.factory('AccountService', ['$resource', AccountService]);