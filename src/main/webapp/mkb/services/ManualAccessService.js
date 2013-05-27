var ManualAccessService = function ($resource) {
  return {
    doAction: function (username, service, action, params, callback) {
      return $resource('/api/manual/do/:service').query({
        username: username,
        action: action,
        params: params
      }, callback);
    },
    login: function (username, password, callback) {
      return $resource('/api/manual/login').query({
        username: username,
        password: password
      }, callback);
    }
  }
};

app.factory('ManualAccessService', ['$resource', ManualAccessService]);