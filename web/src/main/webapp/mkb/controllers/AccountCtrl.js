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
      autowidth:true,
      rowNum: -1,
      scroll: true,
      colNames:['��������', '����', '�Ǽ�', 'Cost'],
      colModel:[
        {name: 'cardName', sorttype:'text'},
        {name: 'race', sorttype:'int', formatter: function(cellvalue, options, rowObject) {
          switch(cellvalue) {
            case 1:
              return '����';
            case 2:
              return 'ɭ��';
            case 3:
              return '����';
            case 4:
              return '����';
          }
          return 'ħ��';
        }},
        {name: 'color', sorttype:'int'},
        {name: 'cost', sorttype:'int'}
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

