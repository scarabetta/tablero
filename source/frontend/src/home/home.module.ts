import * as angular from "angular";
import {routes} from "./home.routes.ts";
import {HomeController} from "./home.controller.ts";
import {formProjectComponent} from "./form.project.component.ts";
import {formOperativeObjectiveComponent} from "./form.operative.objective.component";
import {formStrategicObjectiveComponent} from "./form.strategic.objective.component";
import {formLabelsComponent} from "./form.labels.component";
import {moveProjectComponent} from "./form-project/move.project.component.ts";
import {viewProjectComponent} from "./view-project/view.project.component.ts";
import {GeneralServices} from "../services/services.ts";
import {Search} from "../services/search.ts";
import {alertModalDirective} from "../alert/alertmodal.ts";
import {sideMenuDirective} from "./sidemenu.directive.ts";
import {usigAutocompleteDirective} from './autocomplete.directive.ts';
import {popoverDirective} from './popover.directive.ts';

module Home {

    export const home = angular.module('app.home', ['ui.router', 'ui.bootstrap', 'ngFileUpload', 'angular-carousel', 'ngTouch'])
        .config(routes)
        .controller('homeCtrl', HomeController)
        .component('formproject', formProjectComponent)
        .component('formoperativeobjective', formOperativeObjectiveComponent)
        .component('formstrategicobjective', formStrategicObjectiveComponent)
        .component('formlabels', formLabelsComponent)
        .component('moveproject', moveProjectComponent)
        .component('viewproject', viewProjectComponent)
        .directive('alertmodal', alertModalDirective)
        .directive('sidemenu', sideMenuDirective)
        .service('services', GeneralServices)
        .service('search', Search)
        .directive("usigautocomplete", usigAutocompleteDirective)
        .directive("popover", popoverDirective)
        .name;

}

export = Home;
