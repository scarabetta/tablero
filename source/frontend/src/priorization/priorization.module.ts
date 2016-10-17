import * as angular from "angular";
import {routes} from "./priorization.routes.ts";
import {PriorizationController} from "./priorization.controller.ts";
import {GeneralServices} from "../services/services.ts";
import {upload} from './upload/upload.module.ts';

module Priorization {

    export const priorization = angular.module('app.priorization', ['ui.router', 'ui.bootstrap', upload])
        .config(routes)
        .controller('priorizationCtrl', PriorizationController)
        .service('services', GeneralServices)
        .name;

}

export = Priorization;
