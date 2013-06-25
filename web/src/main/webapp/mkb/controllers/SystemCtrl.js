app.controller('SystemCtrl', function($scope, SystemService, AssetsService) {

  var readCompareOperator = function(operator) {
    switch(operator) {
      case 'GreaterThan': return '大于';
      case 'GreaterThanOrEqualTo': return '大于等于';
      case 'EqualTo': return '等于';
      case 'NotEqualTo': return '不等于';
      case 'LessThan': return '小于';
      case 'LessThanOrEqualTo': return '小于等于';
    }
    return '未知对比';
  };

  var readCurrency = function(currency) {
    switch(currency) {
      case 'Coins': return '金币';
      case 'Cash': return '晶钻';
      case 'Ticket': return '魔幻券';
    }
    return '未知货币';
  };

  var me = {
    accountFilters: [],

    getAssets: function() {
//      me.mapDefs = AssetsService.getMapDefs();
//      me.skillDefs = AssetsService.getSkillDefs();
      me.cardDefs = AssetsService.getCardDefs();
//      me.runeDefs = AssetsService.getRuneDefs();
    },

    refreshAccounts: function() {
      SystemService.findAccounts(me.accountFilters, function(accounts) {
        me.accountsGrid = new AccountsGrid(accounts);
      });
    },

    addFilter: function() {
      var filter = {type: me.search};
      if(me.search == 'level') {
        filter.compare = me.levelCompare;
        filter.value = me.levelValue
      } else if(me.search == 'card') {
        filter.id = me.cardId;
        filter.number = me.cardNumber;
      } else if(me.search == 'currency') {
        filter.currency = me.currencyType;
        filter.compare = me.currencyCompare;
        filter.value = me.currencyValue;
      } else if(me.search == 'legion') {
        filter.name = me.legionName;
      }
      me.accountFilters.push(filter);
    },

    readFilter: function(filter) {
      var ret = '';
      switch(filter.type) {
        case 'level':
          ret += '等级';
          ret += readCompareOperator(filter.compare);
          ret += filter.value;
          break;
        case 'card':
          ret += '拥有';
          ret += filter.number + '张';
          ret += me.cardDefs[filter.id].cardName;
          break;
        case 'currency':
          ret += '拥有';
          ret += readCompareOperator(filter.compare);
          ret += filter.value;
          ret += readCurrency(filter.currency);
          break;
        case 'legion':
          ret += '属于名字中带有“';
          ret += filter.name;
          ret += '”的军团';
          break;
      }
      return ret;
    },

    removeFilter: function(filter) {
      me.accountFilters = $.grep(me.accountFilters, function(value) {
        return value != filter;
      });
    },

    clearFilters: function() {
      me.accountFilters = [];
    }
  };

  me.getAssets();
  me.refreshAccounts();

  $scope.system = me;
});