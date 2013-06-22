app.controller('AssetsCtrl', function($scope, AssetsService) {

  var me = {
    maps: {},
    skills: {},
    cards:{},
    runes: {},

    getAssets: function() {
      AssetsService.getMapDefs(function(maps) {
        me.maps = maps;
      });
      AssetsService.getSkillDefs(function(skills) {
        me.skills = skills;
      });
      AssetsService.getCardDefs(function(cards) {
        me.cards = cards;
      });
      AssetsService.getRuneDefs(function(runes) {
        me.runes = runes;
      });
    },

    refreshAssets: function(username) {
      AssetsService.refreshAssets(username, function() {
        me.getAssets();
      });
    }

  };

  me.getAssets();


  $scope.assets = me;

});