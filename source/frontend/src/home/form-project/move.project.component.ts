import {Proyecto} from "../../models/jurisdiccion";
import {Jurisdiccion} from "../../models/jurisdiccion.ts";
const template = require('./move-project.html');

module Home {

  export class MoveProjectController {

    private currentproject:Proyecto;
    private jurisdiccion:Jurisdiccion;

    /*@ngInject*/
    constructor() {
      console.log('New modal');
      console.log(this.currentproject);
      console.log(this.jurisdiccion);
    }

    changeOperativeObjective(id) {
      this.currentproject.idObjetivoOperativo2 = id;
    }

    cancel() {
      var modal = document.getElementsByTagName('moverModal');
      angular.element(modal).remove();
    }

  }

  export let moveProjectComponent = {
      bindings: {
          currentproject: '<',
          jurisdiccion: '<'
      },
      templateUrl: template,
      controller: MoveProjectController,
      controllerAs: 'moveProjectCtrl',
  };
}

export = Home;
