import * as angular from "angular";
import {routes} from "./cross.topics.routes.ts";
import {CrossTopicsController} from "./cross.topics.controller.ts";
import {GeneralServices} from "../services/services.ts";
import {crossTopicsFormComponent} from "./form-cross-topic/form.cross.topic.component.ts";

module CrossTopics {

    export const crossTopics = angular.module('app.cross.topics', ['ui.router', 'ui.bootstrap'])
        .config(routes)
        .controller('crossTopicsCtrl', CrossTopicsController)
        .component('crosstopicform', crossTopicsFormComponent)
        .service('services', GeneralServices)
        .name;

}

export = CrossTopics;
