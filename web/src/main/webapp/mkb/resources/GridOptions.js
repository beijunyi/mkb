function GridOptions(title, columns, models, formatters, data) {
  var me = this;
  me.data = data || [];
  me.colNames = columns;
  me.colModel = models;
  me.datatype = 'local';
  me.rowNum = -1;
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