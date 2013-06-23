app.controller('AccountCtrl', function($scope, $rootScope, $window, AccountService) {

  var view = $('div.mkb-view');

  var race = function(cellvalue, options, rowObject) {
    switch(cellvalue) {
      case 1: return '����';
      case 2: return 'ɭ��';
      case 3: return '����';
      case 4: return '����';
    }
    return 'ħ��';
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
      colNames:['��������', '����', 'ǿ���ȼ�', 'Cost'],
      colModel:[
        {name: 'cardName', sorttype:'text'},
        {name: 'race', sorttype:'int', formatter: race},
        {name: 'color', sorttype:'int'},
        {name: 'cost', sorttype:'int'}
      ],
      caption: '���п���'
    },

    userCards: {
      data: [],
      datatype: "local",
      autowidth:true,
      rowNum: -1,
      scroll: true,
      colNames:['��������', 'ǿ���ȼ�'],
      colModel:[
        {name: 'cardId', sorttype:'int', formatter: cardName},
        {name: 'level', sorttype:'int'}
      ],
      caption: '���п���'
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

