import {Proyecto} from "../../models/jurisdiccion";
const template = require('./move-project.html');

module Home {

  export class MoveProjectController {

    private currentproject:Proyecto;

    /*@ngInject*/
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
