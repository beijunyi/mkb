var AssetsService = function ($resource) {
  return {
    refreshAssets: function(username, callback) {
      return $resource('/api/assets/refresh').get({
        username: username
      }, callback);
    },
    getMapDefs: function(callback) {
      return $resource('/api/assets/maps').query({}, {}, callback);
    },
    getSkillDefs: function(callback) {
      return $resource('/api/assets/skills').query({}, {}, callback);
    },
    getCardDefs: function(callback) {
      return $resource('/api/assets/cards').get({}, callback);
    },
    getRuneDefs: function(callback) {
      return $resource('/api/assets/runes').query({}, {}, callback);
    }
  }
};

app.factory('AssetsService', ['$resource', AssetsService]);