const template = require('./notification.html');

module Notification {

    export class NotificationController {

      private type: string;// tslint:disable-line
      private icon: string;// tslint:disable-line
      private text: string;// tslint:disable-line
      private title: string;// tslint:disable-line
      private gotoform: boolean;// tslint:disable-line
      private gotoestrategico: boolean;// tslint:disable-line
      private gotooperativo: boolean;// tslint:disable-line
      private textlink: string;// tslint:disable-line

      /*@ngInject*/
      constructor() {
        console.log(this.gotoform);
        console.log(this.gotoestrategico);
        console.log(this.gotooperativo);
      };

      goToCurrentForm() {
        (<any>$(document.getElementsByTagName('notification'))).hide();
          var form = document.getElementsByTagName('formproject');
          (<any>$('html,body')).animate({
            scrollTop: $(form).offset().top},
          'slow');
      }

      goToCurrentEstrategico() {
        (<any>$(document.getElementsByTagName('notification'))).hide();
        var form = document.getElementsByTagName('formstrategicobjective');
        (<any>$('html,body')).animate({
          scrollTop: $(form).offset().top},
        'slow');
      }

      goToCurrentOperativo() {
        (<any>$(document.getElementsByTagName('notification'))).hide();
        var form = document.getElementsByTagName('formoperativeobjective');
        (<any>$('html,body')).animate({
          scrollTop: $(form).offset().top},
        'slow');
      }

    }

    export let notificationComponent = {
        bindings: {
            type: '@',
            icon: '@',
            text: '@',
            title: '@',
            gotoform: '=',
            gotoestrategico: '=',
            gotooperativo: '=',
            textlink: '@'
        },
        templateUrl: template,
        controller: NotificationController,
        controllerAs: 'notificationCtrl',
    };

}

export = Notification;
