import {routes} from "./home.routes.ts";
import * as angular from "angular";
import {HomeController} from "./home.controller.ts";
import {formProjectComponent} from "./form.project.component.ts";
import {formOperativeObjectiveComponent} from "./form.operative.objective.component";
import {formStrategicObjectiveComponent} from "./form.strategic.objective.component";
import {moveProjectComponent} from "./form-project/move.project.component.ts";
import {GeneralServices} from "../services/services.ts";
import {Search} from "../services/search.ts";
import {usigAutocompleteDirective} from './autocomplete.directive.ts';

module Home {

    export const home = angular.module('app.home', ['ui.router', 'ui.bootstrap', 'ngFileUpload', 'angular-carousel', 'ngTouch'])
        .config(routes)
        .controller('homeCtrl', HomeController)
        .component('formproject', formProjectComponent)
        .component('formoperativeobjective', formOperativeObjectiveComponent)
        .component('formstrategicobjective', formStrategicObjectiveComponent)
        .component('moveproject', moveProjectComponent)
        .service('services', GeneralServices)
        .service('search', Search)
        .directive("usigautocomplete", usigAutocompleteDirective)
        .name;

}

export = Home;
