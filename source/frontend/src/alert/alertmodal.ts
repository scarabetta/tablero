const template = require('./alertmodal.html');

module Home {

  export class AlertController {

    constructor(private $scope: ng.IScope) {}

    accept() {
      (<any>$('#' + (<any>this.$scope).modalId)).modal('hide');
      (<any>this.$scope).onAccept();
    }

    cancel() {
      (<any>$('#' + (<any>this.$scope).modalId)).modal('hide');
      (<any>this.$scope).onCancel();
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
        onAccept: '&',
        onCancel: '&'
      },
      controller: AlertController,
      controllerAs: 'alertCtrl'
    };
  };
}

export = Home;
