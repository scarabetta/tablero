const config = require('webpack-config-loader!app-config');
import * as angular from "angular";
import 'angular-recaptcha';
import {routes} from "./login.routes.ts";
import {LoginController} from "./login.controller.ts";
import {GeneralServices} from "../services/services.ts";

module Login {

    export const login = angular.module('app.login', ['ui.router', 'ui.bootstrap', 'vcRecaptcha'])
        .config(routes)
        .config(function(vcRecaptchaServiceProvider){
          vcRecaptchaServiceProvider.setSiteKey(config.captcha.public);
        })
        .controller('loginCtrl', LoginController)
        .service('services', GeneralServices)
        .name;

}

export = Login;
