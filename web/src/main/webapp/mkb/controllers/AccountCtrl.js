app.controller('AccountCtrl', function($scope, $rootScope, $window, AccountService) {

  var view = $('div.mkb-view');

  var race = function(cellvalue, options, rowObject) {
    switch(cellvalue) {
      case 1: return '王国';
      case 2: return '森林';
      case 3: return '蛮荒';
      case 4: return '地狱';
    }
    return '魔神';
  };

  var cardName = function(cellvalue, options, rowObject) {
    if($scope.account.cardDefs.data[cellvalue])
      return $scope.account.cardDefs.data[cellvalue].cardName;
    else
      return '*** UNKNOWN CARD ***' + cellvalue;
  };

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
      autowidth:true,
      rowNum: -1,
      scroll: true,
      colNames:['卡牌名称', '种族', '强化等级', 'Cost'],
      colModel:[
        {name: 'cardName', sorttype:'text'},
        {name: 'race', sorttype:'int', formatter: race},
        {name: 'color', sorttype:'int'},
        {name: 'cost', sorttype:'int'}
      ],
      caption: '所有卡牌'
    },

    userCards: {
      data: [],
      datatype: "local",
      autowidth:true,
      rowNum: -1,
      scroll: true,
      colNames:['卡牌名称', '强化等级'],
      colModel:[
        {name: 'cardId', sorttype:'int', formatter: cardName},
        {name: 'level', sorttype:'int'}
      ],
      caption: '所有卡牌'
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
          me.userCards.data = cards;
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

