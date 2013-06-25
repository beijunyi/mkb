var SystemService = function ($resource) {
  return {
    findAccounts: function (filters, callback) {
      return $resource('/api/system/accounts').save({
        filters: filters
      }, callback);
    }
  }
};

app.factory('SystemService', ['$resource', SystemService]);