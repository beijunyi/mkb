app.controller('ManualAccessCtrl', function($scope, ManualAccessService) {

  $scope.manual = {
    username: '',
    password: '',

    server: '',
    alias: '',
    gender: '',
    inviteCode: '',
    level: '',
    energy: '',
    hp: '',
    cost: '',
    gold: '',
    diamond: '',
    cards: '',
    friends: '',
    legion: '',
    ranking: '',
    collection: '',

    login: function() {
      var me = $scope.manual;
      ManualAccessService.login(me.username, me.password, function(obj) {
        alert(obj);
      })
    }
  }

});