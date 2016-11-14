// import {IndicadorEstrategico} from "../../models/jurisdiccion";
const template = require('./project-indicador.html');

module Home {

  export class IndicadorProjectController {

    // private currentindicador:IndicadorEstrategico;

    /*@ngInject*/
    constructor( private $compile: ng.ICompileService, private $scope:ng.IScope, private $timeout:ng.ITimeoutService) {

    }

  }

  export let indicadorProjectComponent = {
    bindings: {
        currentindicador: '<'
    },
      templateUrl: template,
      controller: IndicadorProjectController,
      controllerAs: 'indicadorCtrl',
  };
}

export = Home;
