import * as angular from "angular";
import {routes} from "./preview.routes.ts";
import {PreviewController} from "./preview.controller.ts";
import {GeneralServices} from "../../services/services.ts";

module Preview {

    export const preview = angular.module('app.preview', ['ui.router', 'ui.bootstrap'])
        .config(routes)
        .controller('previewCtrl', PreviewController)
        .service('services', GeneralServices)
        .name;

}

export = Preview;
