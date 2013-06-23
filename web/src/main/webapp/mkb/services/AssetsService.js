var AssetsService = function ($resource) {
  var me = {
    refreshAssets: function(username, callback) {
      delete me.mapDefs;
      delete me.skillDefs;
      delete me.cardDefs;
      delete me.runeDefs;
      return $resource('/api/assets/refresh').get({
        username: username
      }, callback);
    },

    getMapDefs: function(callback) {
      if(me.mapDefs) {
        if(callback) callback(me.mapDefs);
        return me.mapDefs;
      }
      return $resource('/api/assets/maps').get({}, function(mapDefs) {
        me.mapDefs = mapDefs;
        if(callback) callback(mapDefs);
      });
    },

    getSkillDefs: function(callback) {
      if(me.skillDefs) {
        if(callback) callback(me.skillDefs);
        return me.skillDefs;
      }
      return $resource('/api/assets/skills').get({}, function(skillDefs) {
        me.skillDefs = skillDefs;
        if(callback) callback(skillDefs);
      });
    },

    getCardDefs: function(callback) {
      if(me.cardDefs) {
        if(callback) callback(me.cardDefs);
        return me.cardDefs;
      }
      return $resource('/api/assets/cards').get({}, function(cardDefs) {
        me.cardDefs = cardDefs;
        if(callback) callback(cardDefs);
      });
    },

    getRuneDefs: function(callback) {
      if(me.runeDefs) {
        if(callback) callback(me.runeDefs);
        return me.runeDefs;
      }
      return $resource('/api/assets/runes').get({}, function(runeDefs) {
        me.runeDefs = runeDefs;
        if(callback) callback(runeDefs);
      });
    }
  };
  return me;
};

app.factory('AssetsService', ['$resource', AssetsService]);