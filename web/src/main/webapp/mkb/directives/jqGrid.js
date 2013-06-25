app.directive('jqGrid', function($window){
  return {
    restrict: 'E',
    scope: {
      options: '=',
      api: '='
    },
    template: '<div class="jq-grid"><table class="jq-grid-table"></table><div class="jq-grid-pager"></div></div>',
    controller: function($scope, $element) {
      $scope.$watch('options', function(options, old) {
        if(options) {
          var table = $element.find('table.jq-grid-table').uniqueId();
          options.pager = $element.find('div.jq-grid-pager');
          if(typeof options.data == 'object') {
            options.data = $.map(options.data, function(def) {
              if(typeof def == 'object') return def;
              return null;
            })
          }
          angular.element($window).bind('resize', function() {
            table.setGridWidth($element.width() - 2).setGridHeight($element.height() - 55);
          });
          table.jqGrid(options).setGridWidth($element.width() - 2).setGridHeight($element.height() - 55);
        }
      });

      $scope.$watch('options.data', function(data, old) {
        var table = $element.find('table.jq-grid-table');
        if(data)
        table.jqGrid("clearGridData", true).jqGrid('setGridParam', {
          data: data
        }).trigger('reloadGrid');
      });
    },
    replace: true,
    link: function(scope, elem, attr) {
      if(!attr.api) return;
      var table = elem.find('table.jq-grid-table');
      scope.api = {
        getSelectedRows: function() {
          var rows = table.jqGrid('getGridParam','selarrrow');
          var ret = [];
          $.each(rows, function(index, value) {
            ret.push(table.jqGrid('getRowData', value));
          });
          return ret;
        }
      }
    }
  };
});