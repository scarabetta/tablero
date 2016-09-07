import * as angular from "angular";
import 'angular-recaptcha';
import {routes} from "./login.routes.ts";
import {LoginController} from "./login.controller.ts";
import {GeneralServices} from "../services/services.ts";

module Login {

  // let configModule = angular.module('config');

    export const login = angular.module('app.login', ['ui.router', 'ui.bootstrap', 'vcRecaptcha', 'config'])
        .config(routes)
        .config(function(vcRecaptchaServiceProvider, urlsConfig){
          vcRecaptchaServiceProvider.setSiteKey(urlsConfig.captcha);
        })
        .controller('loginCtrl', LoginController)
        .service('services', GeneralServices)
        .name;

}

export = Login;
