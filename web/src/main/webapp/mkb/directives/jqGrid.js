app.directive('jqGrid', function($window){
  return {
    restrict: 'E',
    scope: {
      options: '='
    },
    template: '<div class="jq-grid"><table class="jq-grid-table"></table><div class="jq-grid-pager"></div></div>',
    controller: function($scope, $element) {
      $scope.$watch('options', function(options) {
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

      $scope.$watch('options.data', function(data) {
        var table = $element.find('table.jq-grid-table');
        if(data)
        table.jqGrid("clearGridData", true).jqGrid('setGridParam', {
          data: data
        }).trigger('reloadGrid');
      });
    },
    replace: true,
    link: function(scope, elem, attr) {

    }
  };
});