app.controller('AccountCtrl', function($scope, $rootScope, $window, AccountService, AssetsService) {
  var me = {
    username: $.cookie('account_username'),
    password: '',
    remember: true,
    maxTry: 5,

    friends: [],
    cards: [],

    getAssets: function() {
//      me.mapDefs = AssetsService.getMapDefs();
//      me.skillDefs = AssetsService.getSkillDefs();
      AssetsService.getCardDefs(function(cardDefs) {
        me.cardDefsGrid = new AssetsCardDefsGrid(cardDefs);
      });
//      me.runeDefs = AssetsService.getRuneDefs();
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

        AccountService.getFriends(me.username, false, function(friends) {
          me.friends = friends;
          me.userFriendsGrid = new UserFriendsGrid(friends);
        });
        AccountService.getCards(me.username, false, function(cards) {
          me.cards = cards;
            AssetsService.getCardDefs(function(cardDefs) {
            me.userCardsGrid = new UserCardGrid(cards, cardDefs);
          });
        });
      });
    },

    logout: function() {
      delete me.user;
      $.removeCookie('account_username');
    },

    refreshUserInfo: function() {
      AccountService.refreshUserInfo(me.username, function(user) {
        me.user = user;
      });
    },

    getMazeStatus: function() {
      if(!me.mazeStatus) {
        AccountService.getMazeStatus(me.username, function(status) {
          me.mazeStatus = status;
        });
      }
    },

    resetMaze: function(id) {
      AccountService.resetMaze(me.username, id, function(status) {
        me.mazeStatus[id] = status;
      });
    },

    clearMaze: function(id) {
      AccountService.clearMaze(me.username, id, me.maxTry, function(status) {
        me.mazeStatus[id] = status;
      });
    },

    getGoodsList: function() {
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

