import {IStateProvider} from "angular-ui-router";
import {LoginController} from "./login.controller.ts";
const templateUrl = require('./login.html');

module Login {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: templateUrl,
                controller: LoginController,
                controllerAs: 'loginCtrl',
                parent: 'root',
                data: {
                  requireLogin: false
                }
            });
    }
}

export = Login;
