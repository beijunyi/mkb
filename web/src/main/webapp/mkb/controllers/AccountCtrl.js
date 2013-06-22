app.controller('AccountCtrl', function($scope, $rootScope, $window, AccountService) {

  var view = $('div.mkb-view');

  var me = {
    username: $.cookie('account_username'),
    password: '',
    remember: true,

    user: new User(),
    friends: [],
    cards: [],

    cardDefs: {
      data: [],
      datatype: "local",
      height: 500,
      rowNum: 50,
      scroll: 1,
      colNames:['¿¨ÅÆÃû³Æ', 'ÐÇ¼¶'],
      colModel:[
        {name: 'cardName', index:'cardName', width:60},
        {name: 'color', index:'color', width:90}
      ],
      multiselect: true,
      caption: "Manipulating Array Data"
    },

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

  var resize = function() {
    view.height($(window).height() - (view.offset().top + 40));
  };
  angular.element($window).bind('resize', resize);
  resize();

  $scope.$on('assets.cards', function(event, cards) {
    me.cardDefs.data = cards;
  });

});

