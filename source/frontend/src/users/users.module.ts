import * as angular from "angular";
import {routes} from "./users.routes.ts";
import {UsersController} from "./users.controller.ts";
import {GeneralServices} from "../services/services.ts";
import {userFormComponent} from "./form-user/form.user.component.ts";

module Users {

    export const users = angular.module('app.users', ['ui.router', 'ui.bootstrap'])
        .config(routes)
        .controller('usersCtrl', UsersController)
        .component('userform', userFormComponent)
        .service('services', GeneralServices)
        .name;

}

export = Users;
