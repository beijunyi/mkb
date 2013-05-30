app.controller('ManualAccessCtrl', function($scope, ManualAccessService) {

  $scope.manual = {
    username: '',
    password: '',

    host: '',
    server: '',
    alias: '',
    gender: '',
    inviteCode: '',
    level: '',
    energy: '',
    hp: '',
    leadership: '',
    gold: '',
    diamond: '',
    cards: '',
    friends: '',
    legion: '',
    ranking: '',
    collection: '',

    service: '',
    action: '',
    key: '',
    value: '',
    paramKeys: [],
    params: {},



    latestUserInfo: null,

    displayResponse: function(obj) {
      $('textarea').val(JSON.stringify(obj, null, 4));
    },

    doAction: function() {
      var me = $scope.manual;
      ManualAccessService.doAction(me.username, me.service, me.action, me.params, function(obj) {
        me.displayResponse(obj);
      });
    },

    addProperty: function() {
      var me = $scope.manual;
      me.paramKeys.push(me.key);
      me.params[me.key] = me.value;
      me.key = '';
      me.value = '';
    },

    removeProperty: function(key) {
      var me = $scope.manual;
      me.paramKeys = $.grep(me.paramKeys, function(value) {
        return value != key;
      });
      delete me.params[key]
    },

    login: function() {
      var me = $scope.manual;
      ManualAccessService.login(me.username, me.password, function(obj) {
        me.displayResponse(obj);
        me.host = obj.cdnurl;
      });
    },

    getUserInfo: function() {
      var me = $scope.manual;
      if(!me.username) return;
      ManualAccessService.getUserInfo(me.username, function(obj) {
        me.displayResponse(obj);
        me.latestUserInfo = obj;
        me.alias = obj.nickName;
        me.leadership = obj.leaderShip;
        me.level = obj.level;
        me.inviteCode = obj.inviteCode;
        me.energy = obj.energy;
        me.gender = obj.sex;
        me.gold = obj.coins;
        me.diamond = obj.cash;
      });
    }
  }

});