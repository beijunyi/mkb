app.controller('AccountCtrl', function($scope, AccountService) {

  var me = {

    username: $.cookie('account_username'),
    password: '',
    remember: true,

    user: new User(),
    friends: [],
    cards: [],


    login: function() {
      AccountService.login(me.username, me.password, false, function(user) {
        if(me.remember) {
          $.cookie('account_username', me.username, {expires: 7});
        }
        me.user = user;
        AccountService.getFriends(me.username, false, function(friends) {
          me.friends = friends;
        });
        AccountService.getCards(me.username, false, function(cards) {
          me.cards = cards;
        });
      });
    }

  };

  if(me.username) {
    me.login();
  }


  $scope.account = me;

});

