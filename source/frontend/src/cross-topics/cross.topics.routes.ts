import {IStateProvider} from "angular-ui-router";
import {CrossTopicsController} from "./cross.topics.controller.ts";
const templateUrl = require('./cross-topics.html');

module CrossTopics {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('cross', {
                url: '/cross/topics',
                templateUrl: templateUrl,
                controller: CrossTopicsController,
                controllerAs: 'crossTopicsCtrl',
                parent: 'root',
                data: {
                  requireLogin: true
                }
            });
    }
}

export = CrossTopics;
