import {IStateProvider} from "angular-ui-router";
import {PreviewController} from "./preview.controller.ts";
const templateUrl = require('./preview.html');

module Preview {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('preview', {
                url: '/preview',
                templateUrl: templateUrl,
                controller: PreviewController,
                controllerAs: 'previewCtrl',
                parent: 'root',
                params: {
                  'projects': null,
                },
                data: {
                  requireLogin: true
                }
            });
    }
}

export = Preview;
