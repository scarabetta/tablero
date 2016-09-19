import {IStateProvider} from "angular-ui-router";
import {ResultsController} from "./results.controller.ts";
const templateUrl = require('./results.html');

module Results {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('results', {
                url: '/results',
                templateUrl: templateUrl,
                controller: ResultsController,
                controllerAs: 'resultsCtrl',
                parent: 'root',
                params: {
                  'results': null,
                },
                data: {
                  requireLogin: true
                }
            });
    }
}

export = Results;
