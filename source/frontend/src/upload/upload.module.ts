import * as angular from "angular";
import {routes} from "./upload.routes.ts";
import {UploadController} from "./upload.controller.ts";
import {GeneralServices} from "../services/services.ts";

module Upload {

    export const upload = angular.module('app.upload', ['ui.router', 'ui.bootstrap'])
        .config(routes)
        .controller('uploadCtrl', UploadController)
        .service('services', GeneralServices)
        .name;

}

export = Upload;
