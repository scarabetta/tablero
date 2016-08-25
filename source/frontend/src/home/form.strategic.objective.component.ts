import {GeneralServices} from "../services/services.ts";
import {ObjetivoJurisdiccional} from "../models/jurisdiccion";
import {IndicadorEstrategico} from "../models/jurisdiccion";

const template = require('./form-strategic-objective.html');

module Home {

  export class FormStrategicObjectiveComponentController {

    private idjurisdiccion: number;
    private idobjetivoestrategico: number;
    private currentStrategicObjective: ObjetivoJurisdiccional;

    /*@ngInject*/
    constructor(private services:GeneralServices, private $http: ng.IHttpService,
      private $state:ng.ui.IStateService, private $compile: ng.ICompileService, private $scope:ng.IScope) {

      if (this.idobjetivoestrategico) {
        services.getStrategicObjective(this.idobjetivoestrategico).then((data) => {
          this.currentStrategicObjective = data;
        });
      } else {
        this.currentStrategicObjective = <ObjetivoJurisdiccional>{};
      }
    }

    saveStrategicObjective() {
      if (this.currentStrategicObjective) {
        if (!this.currentStrategicObjective.idJurisdiccionAux) {
          this.currentStrategicObjective.idJurisdiccionAux = this.idjurisdiccion;
        }
        if (this.currentStrategicObjective.idObjetivoJurisdiccional) {
          this.services.updateStrategicObjective(this.currentStrategicObjective).then((data) => {
            this.$state.reload();
          });
        } else {
          this.services.saveStrategicObjective(this.currentStrategicObjective).then((data) => {
            this.$state.reload();
          });
        }
      }
    }

    cancelStrategicObjective() {
      var formDiv = document.getElementsByTagName('formstrategicobjective');
      angular.element(formDiv).remove();
    }

    deleteStrategicObjective(id) {
        if (this.currentStrategicObjective.objetivosOperativos.length > 0) {
            var referralDivFactory = this.$compile(' <div class="alert alert-warning"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>Alerta!</strong> No puede borrar el Objetivo Estratégico si posee Objetivos Operativos.</div> ' ); // tslint:disable-line
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById(id);
            angular.element(containerDiv).append(referralDiv);
        } else {
         this.services.deleteStrategicObjective(id).then((data) => {
             this.$state.reload();
         });
        }
    }

    addIndicador() {
      if (!this.currentStrategicObjective.indicadoresEstrategicos) {
        this.currentStrategicObjective.indicadoresEstrategicos = new Array<IndicadorEstrategico>();
      }
      this.currentStrategicObjective.indicadoresEstrategicos.push(<IndicadorEstrategico>{});
    }

    removeIndicador(index) {
      this.currentStrategicObjective.indicadoresEstrategicos.splice(index, 1);
    }

  }

  export let formStrategicObjectiveComponent = {
      bindings: {
          idjurisdiccion: '=',
          idobjetivoestrategico: '='
      },
      templateUrl: template,
      controller: FormStrategicObjectiveComponentController,
      controllerAs: 'formCtrl',
  };
}

export = Home;
