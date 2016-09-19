const template = require('./alertmodal.html');

module Home {

  export class AlertController {

    constructor(private $scope: ng.IScope) {}

    accept() {
      (<any>$('#' + (<any>this.$scope).modalId)).modal('hide');
      (<any>this.$scope).onAccept();
    }
  }

  /*@ngInject*/
  export function alertModalDirective() {
    return {
      restrict: 'E',
      templateUrl: template,
      scope: {
        modalId: '@',
        modalTitle: '@',
        modalBody: '@',
        onAccept: '&'
      },
      controller: AlertController,
      controllerAs: 'alertCtrl'
    };
  };
}

export = Home;
