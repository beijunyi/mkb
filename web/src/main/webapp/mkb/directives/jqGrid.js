app.directive('jqGrid', function($window){
  return {
    restrict: 'E',
    scope: {
      grid: '='
    },
    template: '<div class="jq-grid"><table class="jq-grid-table"></table><div class="jq-grid-pager"></div></div>',
    controller: function($scope, $element) {
      $scope.$watch('grid', function(grid) {
        if(grid) {
          var table = $element.find('table.jq-grid-table').uniqueId();
          grid.pager = $element.find('div.jq-grid-pager');

          grid.getSelectedRows = function() {
            var rows = table.jqGrid('getGridParam','selarrrow');
            var ret = [];
            $.each(rows, function(index, value) {
              ret.push(table.jqGrid('getRowData', value));
            });
            return ret;
          };

          angular.element($window).bind('resize', function() {
            table.setGridWidth($element.width() - 2).setGridHeight($element.height() - 56);
          });
          table.jqGrid(grid).setGridWidth($element.width() - 2).setGridHeight($element.height() - 56);
        }
      });

      $scope.$watch('grid.data', function(data) {
        var table = $element.find('table.jq-grid-table');
        if(data)
          if(typeof data == 'object') {
            data = $.map(data, function(def) {
              if(typeof def == 'object') return def;
              return null;
            })
          }
        table.jqGrid("clearGridData").jqGrid('setGridParam', {
          data: data
        }).trigger('reloadGrid');
      });
    },
    replace: true,
    link: function(scope, elem, attr) {
    }
  };
});