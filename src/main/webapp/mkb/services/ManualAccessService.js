var ManualAccessService = function ($resource) {
  return {
    doAction: function (username, service, action, params, callback) {
      return $resource('/api/manual/do/:service').get({
        username: username,
        service: service,
        do: action,
        params: JSON.stringify(params)
      }, callback);
    },
    login: function (username, password, callback) {
      return $resource('/api/manual/login').get({
        username: username,
        password: password
      }, callback);
    },
    getUserInfo: function (username, callback) {
      return $resource('/api/manual/getUserInfo').get({
        username: username
      }, callback);
    }
  }
};

app.factory('ManualAccessService', ['$resource', ManualAccessService]);