var SystemService = function ($resource) {
  return {
    findAccounts: function (filters, callback) {
      return $resource('/api/system/accounts').save({
        filters: filters
      }, callback);
    },

    addBossPool: function(usernames, callback) {
      return $resource('/api/system/addboss').save({
        usernames: usernames
      }, callback);
    },

    getBossPool: function(callback) {
      return $resource('/api/system/boss').get({}, callback);
    },

    removeBossPool: function(usernames, callback) {
      return $resource('/api/system/removeboss').save({
        usernames: usernames
      }, callback);
    },

    updateBossPool: function(username, bossSettings, callback) {
      return $resource('/api/system/updateboss').save({
        username: username
      }, bossSettings, callback)
    },

    addMapPool: function(usernames, callback) {
      return $resource('/api/system/addmap').save({
        usernames: usernames
      }, callback);
    },

    getMapPool: function(callback) {
      return $resource('/api/system/map').get({}, callback);
    },

    removeMapPool: function(usernames, callback) {
      return $resource('/api/system/removemap').save({
        usernames: usernames
      }, callback);
    },

    updateMapPool: function(username, mapSettings, callback) {
      return $resource('/api/system/updatemap').save({
        username: username
      }, mapSettings, callback)
    },

    addMazePool: function(usernames, callback) {
      return $resource('/api/system/addmaze').save({
        usernames: usernames
      }, callback);
    },

    getMazePool: function(callback) {
      return $resource('/api/system/maze').get({}, callback);
    },

    removeMazePool: function(usernames, callback) {
      return $resource('/api/system/removemaze').save({
        usernames: usernames
      }, callback);
    },

    updateMazePool: function(username, mazeSettings, callback) {
      return $resource('/api/system/updatemaze').save({
        username: username
      }, mazeSettings, callback)
    },

    addFenergyPool: function(usernames, callback) {
      return $resource('/api/system/addfenergy').save({
        usernames: usernames
      }, callback);
    },

    getFenergyPool: function(callback) {
      return $resource('/api/system/fenergy').get({}, callback);
    },

    removeFenergyPool: function(usernames, callback) {
      return $resource('/api/system/removefenergy').save({
        usernames: usernames
      }, callback);
    },

    updateFenergyPool: function(username, fenergySettings, callback) {
      return $resource('/api/system/updatefenergy').save({
        username: username
      }, fenergySettings, callback)
    },

    addFriendsPool: function(usernames, callback) {
      return $resource('/api/system/addfriends').save({
        usernames: usernames
      }, callback);
    },

    getFriendsPool: function(callback) {
      return $resource('/api/system/friends').get({}, callback);
    },

    removeFriendsPool: function(usernames, callback) {
      return $resource('/api/system/removefriends').save({
        usernames: usernames
      }, callback);
    },

    updateFriendsPool: function(username, friendsSettings, callback) {
      return $resource('/api/system/updatefriends').save({
        username: username
      }, friendsSettings, callback)
    },

    addLegionPool: function(usernames, callback) {
      return $resource('/api/system/addlegion').save({
        usernames: usernames
      }, callback);
    },

    getLegionPool: function(callback) {
      return $resource('/api/system/legion').get({}, callback);
    },

    removeLegionPool: function(usernames, callback) {
      return $resource('/api/system/removelegion').save({
        usernames: usernames
      }, callback);
    },

    updatelegionPool: function(username, legionSettings, callback) {
      return $resource('/api/system/updatelegion').save({
        username: username
      }, legionSettings, callback)
    }
  }
};

app.factory('SystemService', ['$resource', SystemService]);