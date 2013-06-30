var ProductionService = function ($resource) {
  return {
    getProductionCriterion: function(callback) {
      return $resource('/api/production/criterion').get({}, callback);
    },

    addProductionCriteria: function(cardCount, callback) {
      return $resource('/api/production/addcriteria').save(cardCount, callback);
    },

    getServers: function(callbeck) {
      return $resource('/api/production/servers').get({}, callbeck);
    },

    produceAccounts: function(settings, callback) {
      return $resource('/api/production/produce').save(settings, callback);
    },

    getProductionUpdate: function(callback) {
      return $resource('/api/production/update').get({}, callback);
    }

  }
};

app.factory('ProductionService', ['$resource', ProductionService]);