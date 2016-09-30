import {IStateProvider} from "angular-ui-router";
import {PriorizationController} from "./priorization.controller.ts";
const templateUrl = require('./priorization.html');

module Priorization {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('priorization', {
                url: '/priorization',
                templateUrl: templateUrl,
                controller: PriorizationController,
                controllerAs: 'priorizationCtrl',
                parent: 'root',
                data: {
                  requireLogin: true
                }
            });
    }
}

export = Priorization;
