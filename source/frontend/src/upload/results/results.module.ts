import * as angular from "angular";
import {routes} from "./results.routes.ts";
import {ResultsController} from "./results.controller.ts";
import {GeneralServices} from "../../services/services.ts";

module Results {

    export const results = angular.module('app.results', ['ui.router', 'ui.bootstrap'])
        .config(routes)
        .controller('resultsCtrl', ResultsController)
        .service('services', GeneralServices)
        .name;

}

export = Results;
