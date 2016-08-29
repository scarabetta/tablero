import {GeneralServices} from "../services/services.ts";

module Login {

    export class LoginController {

      private email:string;
      private password:string;
      private captcha:string;
      private numberOfAttempts = 0;
      private maxNumberOfAttempts = 3;
      private form:angular.IFormController;
      private tokenKey = 'token';
      private currentUserKey = 'currentUser';

        /*@ngInject*/
        constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private vcRecaptchaService:any, private localStorageService:angular.local.storage.ILocalStorageService,
        private $compile: ng.ICompileService, private $scope:ng.IScope) {}

        login() {
          if (this.captcha || this.numberOfAttempts < this.maxNumberOfAttempts) {
            var userData = {
              "email": this.email,
              "password": this.password
            };
            this.services.login(userData)
            .then((res) => {
              if (res && res.token) {
                this.localStorageService.set(this.tokenKey, res.token);
                this.services.getUserByToken().then((data) => {
                  this.localStorageService.set(this.currentUserKey, data);
                });
                this.$state.go('home.tree');
              } else {
                var notificationData = {
                  "type" : "warning",
                  "icon" : "exclamation-sign",
                  "title" : "Error",
                  "text" : "El email y/o contraseña ingresados son incorrectos." // tslint:disable-line
                };
                this.addNotification(notificationData);
                this.email = '';
                this.password = '';
                this.captcha = '';
                this.numberOfAttempts++;
                this.vcRecaptchaService.reload();
                this.form.$setPristine();
              }
            });
          }
        }

        addNotification(data) {
          var referralDivFactory = this.$compile(' <notification type="' +data.type+ '" icon="' +data.icon+ '" title="' +data.title+ '" text="' +data.text+ '" '+data.action+'="' +data.valueAction+ '" textlink="' +data.textlink+ '"></notification> '); // tslint:disable-line
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById('notificationLogin');
          angular.element(containerDiv).append(referralDiv);

        }

    }
}

export = Login;
