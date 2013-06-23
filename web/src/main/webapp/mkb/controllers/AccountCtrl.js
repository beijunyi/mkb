app.controller('AccountCtrl', function($scope, $rootScope, $window, AccountService, AssetsService) {
  var me = {
    username: $.cookie('account_username'),
    password: '',
    remember: true,

    user: {},
    friends: [],
    cards: [],

    mapDefs:{},
    skillDefs: {},
    cardDefs: {},
    runeDefs: {},

    getAssets: function() {
      me.mapDefs = AssetsService.getMapDefs();
      me.skillDefs = AssetsService.getSkillDefs();
      me.cardDefs = AssetsService.getCardDefs(function(cardDefs) {
        me.cardDefsGrid = new AssetsCardDefsGrid($.map(cardDefs, function(def) {
          if(typeof def == 'object') return def;
          return null;
        }));
      });
      me.runeDefs = AssetsService.getRuneDefs();
    },

    refreshAssets: function(username) {
      AssetsService.refreshAssets(username, function() {
        me.getAssets();
      });
    },

    login: function() {
      AccountService.login(me.username, me.password, false, function(user) {
        if(me.remember) $.cookie('account_username', me.username, {expires: 7});
        me.getAssets();
        me.user = user;

        me.friends = AccountService.getFriends(me.username, false);
        me.cards = AccountService.getCards(me.username, false, function(cards) {
          AssetsService.getCardDefs(function(cardDefs) {
            me.userCardsGrid = new UserCardGrid(cards, cardDefs);
          });
        });
      });
    }

  };

  if(me.username) {
    me.login();
  }

  $scope.account = me;

  // Set up view resize
  var view = $('div.mkb-view');
  var resize = function() {
    view.height($(window).height() - (view.offset().top + 40));
  };
  angular.element($window).bind('resize', resize);
  resize();
});

