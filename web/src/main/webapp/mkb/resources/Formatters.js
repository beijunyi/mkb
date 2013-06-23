function RaceFormatter() {
  return function(value) {
    switch(value) {
      case 1: return '王国';
      case 2: return '森林';
      case 3: return '蛮荒';
      case 4: return '地狱';
    }
    return '魔神';
  }
}

function CardNameFormatter(cardDefs) {
  return function(value) {
    return cardDefs[value].cardName;
  }
}

function RankFightResultFormatter() {
  return function(value, options, record) {
    var ret = record.win + '/' + record.lose;
    var total = record.win + record.lose;
    if(total) {
      ret += ' ' + (record.win / total * 100).toFixed(2) + '%';
    }
    return ret;
  }
}