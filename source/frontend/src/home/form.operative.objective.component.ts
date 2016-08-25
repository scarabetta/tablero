import {GeneralServices} from "../services/services.ts";
import {ObjetivoOperativo} from "../models/jurisdiccion";
const template = require('./form-operative-objective.html');

module Home {

  export class FormOperativeObjectiveComponentController {

    private idobjetivoestrategico: number;
    private idoperativeobjective: number;
    private currentOperativeObjective: ObjetivoOperativo;

    /*@ngInject*/
    constructor(private services:GeneralServices, private $http: ng.IHttpService, private $state:ng.ui.IStateService,
      private $compile: ng.ICompileService, private $scope:ng.IScope) {

      if (this.idoperativeobjective) {
        services.getOperativeObjective(this.idoperativeobjective).then((data) => {
          this.currentOperativeObjective = data;
        });
      }
    }

    saveOperativeObjective() {
      if (this.currentOperativeObjective) {
        if (this.idobjetivoestrategico && !this.currentOperativeObjective.idObjetivoJurisdiccionalAux) {
          this.currentOperativeObjective.idObjetivoJurisdiccionalAux = this.idobjetivoestrategico;
        }
        if (!this.currentOperativeObjective.codigo) {
          this.currentOperativeObjective.codigo = "A DEFINIR";
        }
        if (this.currentOperativeObjective.idObjetivoOperativo) {
          this.services.updateOperativeObjective(this.currentOperativeObjective).then((data) => {
            this.$state.reload();
          });
        } else {
          this.services.saveOperativeObjective(this.currentOperativeObjective).then((data) => {
            this.$state.reload();
          });
        }
      }
    }

    cancelOperativeObjective() {
      var formDiv = document.getElementsByTagName('formoperativeobjective');
      angular.element(formDiv).remove();
    }

    deleteOperativeObjective(id) {
        if (this.currentOperativeObjective.proyectos.length > 0) {
            var referralDivFactory = this.$compile(' <div class="alert alert-warning"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Alerta!</strong> No puede borrar el Objetivo Operativo si posee Proyectos.</div> ' ); // tslint:disable-line
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById(id);
            angular.element(containerDiv).append(referralDiv);
        } else {
            this.services.deleteOperativeObjective(id).then((data) => {
                this.$state.reload();
            });
        }
    }

  }

  export let formOperativeObjectiveComponent = {
      bindings: {
          idobjetivoestrategico: '=',
          idoperativeobjective: '='
      },
      templateUrl: template,
      controller: FormOperativeObjectiveComponentController,
      controllerAs: 'formCtrl',
  };
}

export = Home;
