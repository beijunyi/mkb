function GridOptions(title, columns, models, formatters, data, ext) {
  var me = this;
  me.data = data || [];
  me.colNames = columns;
  me.colModel = models;
  me.datatype = 'local';
  me.rowNum = 5000;
  me.autowidth = true;
  me.scroll = true;
  me.caption = title;
  me.setFormatter = function(name, formatter) {
    $.each(models, function(index, model) {
      if(model.name == name) {
        model.formatter = formatter;
      }
    });
  };
  if(formatters) {
    $.each(formatters, function(name, formatter) {
      me.setFormatter(name, formatter);
    });
  }
  me.setData = function(data) {
    me.data = data;
  };
  if(ext) {
    $.each(ext, function(index, value) {
      me[index] = value;
    })
  }
}

function AssetsCardDefsGrid(data) {
  return new GridOptions(
    '卡牌设定',
    ['卡牌名称', '种族', '星级', 'Cost'],
    [
      {name: 'cardName', sorttype:'text'},
      {name: 'race', sorttype:'int'},
      {name: 'color', sorttype:'int'},
      {name: 'cost', sorttype:'int'}
    ],
    {race: new RaceFormatter()},
    data
  );
}

function UserCardGrid(data, cardDefs) {
  return new GridOptions(
    '玩家卡牌',
    ['卡牌名称', '强化等级'],
    [
      {name: 'cardId', sorttype:'int'},
      {name: 'level', sorttype:'int'}
    ],
    {cardId: new CardNameFormatter(cardDefs)},
    data
  );
}

function UserFriendsGrid(data) {
  return new GridOptions(
    '玩家好友',
    ['好友昵称', '等级', '所属军团', '竞技场排名', '竞技场战况'],
    [
      {name: 'nickName', sorttype:'text'},
      {name: 'level', sorttype:'int'},
      {name: 'legionName', sorttype:'text'},
      {name: 'rank', sorttype:'int'},
      {name: 'win', sorttype:'int'}
    ],
    {win: new RankFightResultFormatter()},
    data
  );
}

function AccountsGrid(data) {
  return new GridOptions(
    '所有账号',
    ['所在服务器', '用户名', '昵称', '等级'],
    [
      {name: 'server', sorttype:'text'},
      {name: 'username', sorttype:'int'},
      {name: 'nickname', sorttype:'text'},
      {name: 'level', sorttype:'int'}
    ],
    {},
    data,
    {multiselect: true, recordpos: 'left'}
  );
}

function CriterionGrid(data, cardDefs) {
  return new GridOptions(
    '开局条件',
    ['拥有卡牌'],
    [
      {name: 'cardCount', sorttype:'text'}
    ],
    {cardCount: new CardCountFormatter(cardDefs)},
    data
  );
}

function ProductionGrid(data, cardDefs) {
  return new GridOptions(
    '量产账号',
    ['用户名', '密码', '昵称', '初始卡牌'],
    [
      {name: 'username', sorttype:'text'},
      {name: 'password', sorttype:'text'},
      {name: 'userInfo'},
      {name: 'newCards'}

    ],
    {
      userInfo: new NicknameFormatter(),
      newCards: new NewCardsFormatter(cardDefs)
    },
    data
  );
}