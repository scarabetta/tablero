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

        /*@ngInject*/
        constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private vcRecaptchaService:any, private localStorageService:angular.local.storage.ILocalStorageService) {}

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
                this.$state.go('home');
              } else {
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

    }
}

export = Login;
