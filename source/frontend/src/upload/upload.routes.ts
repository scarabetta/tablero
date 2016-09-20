import {IStateProvider} from "angular-ui-router";
import {UploadController} from "./upload.controller.ts";
const templateUrl = require('./upload.html');

module Upload {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('upload', {
                url: '/upload',
                templateUrl: templateUrl,
                controller: UploadController,
                controllerAs: 'uploadCtrl',
                parent: 'root',
                data: {
                  requireLogin: true
                }
            });
    }
}

export = Upload;
