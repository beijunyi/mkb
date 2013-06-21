app.controller('AccountCtrl', function($scope, AccountService) {

  var me = {

    username: $.cookie('account_username'),
    password: '',
    remember: true,

    user: new User(),


    login: function() {
      AccountService.login(me.username, me.password, function(user) {
        if(me.remember) {
          $.cookie('account_username', me.username, {expires: 7});
        }
        me.user = user;
      });
    }

  };

  if(me.username) {
    me.login();
  }

  $scope.account = me;

});

