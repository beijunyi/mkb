app.directive('jqGrid', function($window){
  return {
    restrict: 'E',
    scope: {
      options: '='
    },
    template: '<div class="jq-grid"><table class="jq-grid-table"></table><div class="jq-grid-pager"></div></div>',
    controller: function($scope, $element) {
      $scope.$watch('options.data', function(data, old) {
        var table = $element.find('table.jq-grid-table');
        if(data && data.length && data != old)
        table.jqGrid('setGridParam', {
          data: data
        }).trigger('reloadGrid');
      });
    },
    replace: true,
    link: function(scope, elem, attr) {
      var table = elem.find('table.jq-grid-table').uniqueId();
      var pager = elem.find('div.jq-grid-pager');
      var options = scope.options;
      options.pager = pager;
      angular.element($window).bind('resize', function() {
        table.setGridWidth(elem.width()).setGridHeight(elem.height() - 40);
      });
      table.jqGrid(options).setGridWidth(elem.width()).setGridHeight(elem.height() - 40);
    }
  };
});