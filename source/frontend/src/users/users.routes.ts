import {IStateProvider} from "angular-ui-router";
import {UsersController} from "./users.controller.ts";
const templateUrl = require('./users.html');

module Users {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('users', {
                url: '/users',
                templateUrl: templateUrl,
                controller: UsersController,
                controllerAs: 'usersCtrl',
                parent: 'root',
                data: {
                  requireLogin: false
                }
            });
    }
}

export = Users;
