var ProductionService = function ($resource) {
  return {
    getProductionCriterion: function(callback) {
      return $resource('/api/production/criterion').get({}, callback);
    },

    addProductionCriteria: function(cardCount, callback) {
      return $resource('/api/production/addcriteria').save(cardCount, callback);
    }
  }
};

app.factory('ProductionService', ['$resource', ProductionService]);