const template = require('./notification.html');

module Notification {

    export class NotificationController {

      constructor(private $scope: ng.IScope) {};

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

      callback() {
        (<any>this.$scope).callback();
      }

    }

    /*@ngInject*/
    export function notificationDirective() {
      return {
        restrict: 'E',
        templateUrl: template,
        scope: {
          type: '@',
          icon: '@',
          text: '@',
          title: '@',
          gotoform: '=',
          gotoestrategico: '=',
          gotooperativo: '=',
          hascallback: '=',
          textlink: '@',
          callback: '&'
        },
        controller: NotificationController,
        controllerAs: 'notificationCtrl'
      };
    };

}

export = Notification;
