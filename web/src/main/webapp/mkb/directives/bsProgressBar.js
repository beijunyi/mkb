app.directive('bsProgressBar', function($window){
  return {
    restrict: 'E',
    scope: {
      pbar: '='
    },
    template: '<div class="progress"><div class="bar"></div></div>',
    controller: function($scope, $element) {
      var bar = $element.find('div.bar');

      $scope.$watch('pbar', function(pbar) {
        if(pbar) {
          pbar.setStriped = function(state) {
            if(state === false) {
              $element.removeClass('progress-striped');
            } else {
              $element.addClass('progress-striped');
            }
          };

          pbar.setActive = function(state) {
            if(state === false) {
              $element.removeClass('active');
            } else {
              $element.addClass('active');
            }
          };

          pbar.setPercentage = function(percent) {
            bar.css('width', percent + '%');
          };

          pbar.setSuccess = function() {
            bar.addClass('bar-success');
            bar.removeClass('bar-warning');
            bar.removeClass('bar-danger');
          };

          pbar.setWarning = function() {
            bar.addClass('bar-warning');
            bar.removeClass('bar-success');
            bar.removeClass('bar-danger');
          };

          pbar.setDanger = function() {
            bar.addClass('bar-warning');
            bar.removeClass('bar-success');
            bar.removeClass('bar-danger');
          };

          if(pbar.striped) pbar.setStriped(true);
          if(pbar.active) pbar.setActive(true);
          if(pbar.danger) pbar.setDanger();
          else if(pbar.warning) pbar.setWarning();
          else if(pbar.success) pbar.setSuccess();
        }
      });

      $scope.$watch('pbar.percent', function(percent) {
        if(percent) {
          $element.find('div.bar').css('width', percent + '%');
        }
      });
    },
    replace: true,
    link: function(scope, elem, attr) {
    }
  };
});