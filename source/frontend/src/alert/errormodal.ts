const template = require('./errormodal.html');

module Error {

  export class ErrorController {

    constructor(private localStorageService:angular.local.storage.ILocalStorageService) {}

    hide() {
      (<any>$('#errorModal')).modal('hide');
    }

  }

  /*@ngInject*/
  export function errorModalComponent() {
    return {
      restrict: 'E',
      templateUrl: template,
      controller: ErrorController,
      controllerAs: 'errorCtrl'
    };
  };
}

export = Error;
