app.directive('bsProgressBar', function($window){
  return {
    restrict: 'E',
    scope: {
      pbar: '='
    },
    template: '<div class="progress"><div class="bar"></div></div>',
    controller: function($scope, $element) {
      var progress = $element.find('div.progress');
      var bar = $element.find('div.bar');

      $scope.$watch('pbar', function(pbar) {
        if(pbar) {
          pbar.setStriped = function(state) {
            if(state === false) {
              progress.removeClass('progress-striped');
            } else {
              progress.addClass('progress-striped');
            }
          };

          pbar.setActive = function(state) {
            if(state === false) {
              progress.removeClass('active');
            } else {
              progress.addClass('active');
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