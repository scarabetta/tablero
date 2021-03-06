const template = require('./notification.html');

module Notification {

    export class NotificationController {

      constructor(private $scope: ng.IScope) {
        setTimeout(function() {
          $('.alert').fadeOut('slow');
        }, 10000);
      };

      goToCurrentForm() {
        (<any>$(document.getElementsByTagName('notification'))).hide();
          var form = document.getElementsByTagName('formproject');
          (<any>$('html,body')).animate({
            scrollTop: $(form).offset().top - 80},
          'slow');
      }

      goToCurrentEstrategico() {
        (<any>$(document.getElementsByTagName('notification'))).hide();
        var form = document.getElementsByTagName('formstrategicobjective');
        (<any>$('html,body')).animate({
          scrollTop: $(form).offset().top - 80},
        'slow');
      }

      goToCurrentOperativo() {
        (<any>$(document.getElementsByTagName('notification'))).hide();
        var form = document.getElementsByTagName('formoperativeobjective');
        (<any>$('html,body')).animate({
          scrollTop: $(form).offset().top - 80},
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
