import {GeneralServices} from "../services/services";
import {ObjetivoJurisdiccional} from "../models/jurisdiccion";
import {IndicadorEstrategico} from "../models/jurisdiccion";

const template = require('./form-strategic-objective.html');

module Home {

  export class FormStrategicObjectiveComponentController {

    private title: string;
    private idjurisdiccion: number;
    private idobjetivoestrategico: number;
    private currentStrategicObjective: ObjetivoJurisdiccional;
    private currentTotalPesoRelativo: number;
    private currentIndicador: IndicadorEstrategico;
    private validPesoRelativo: string;
    private allProjects = new Array<any>();

    /*@ngInject*/
    constructor(private services:GeneralServices, private $http: ng.IHttpService,
      private $state:ng.ui.IStateService, private $compile: ng.ICompileService, private $scope:ng.IScope) {
      this.validPesoRelativo = "";
      if (this.idobjetivoestrategico) {
        this.title = "Modificar objetivo estratégico";
        services.getStrategicObjective(this.idobjetivoestrategico).then((data) => {
          this.currentStrategicObjective = data;
          this.currentStrategicObjective.objetivosOperativos.forEach((oo) => {
            oo.proyectos.forEach((project) => {
              this.allProjects.push(project);
            });
          });
          this.currentStrategicObjective.indicadoresEstrategicos.forEach((i) => {
            if (i.estado === "Cancelado") {
              i.pesoRelativo = 0;
            }
          });
          this.getCurrentTotalPesoRelativo();
        });
      } else {
        this.title = "Nuevo objetivo estratégico";
        this.currentStrategicObjective = <ObjetivoJurisdiccional>{};
        this.currentTotalPesoRelativo = 0;
      }

      $scope.$watch('formCtrl.currentStrategicObjective.indicadoresEstrategicos', (newVal, oldVal) => {
        if (this.currentStrategicObjective) {
          this.getCurrentTotalPesoRelativo();
        }
      }, true);

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

    deleteStrategicObjectiveById(id) {
        if (this.currentStrategicObjective.objetivosOperativos.length > 0) {
          var notificationData = {
            "type" : "warning",
            "icon" : "exclamation-sign",
            "title" : "Alerta",
            "text" : "No puede borrar el Objetivo Estratégico si posee Proyectos.",
            "action": "gotoestrategico",
            "valueAction" : true,
            "textlink": "Ir al formulario"
          };
          this.addNotification(notificationData);
        } else {
         this.services.deleteStrategicObjective(id).then((data) => {
             this.$state.reload();
         });
        }
    }

    addNotification(data) {
      var referralDivFactory = this.$compile(' <notification type="' +data.type+ '" icon="' +data.icon+ '" title="' +data.title+ '" text="' +data.text+ '" '+data.action+'="' +data.valueAction+ '" textlink="' +data.textlink+ '"></notification> '); // tslint:disable-line
      var referralDiv = referralDivFactory(this.$scope);
      var containerDiv = document.getElementById('notifications');
      angular.element(containerDiv).append(referralDiv);
      this.goToTop();
    }

    getCurrentTotalPesoRelativo() {
      this.validPesoRelativo = "";
      this.currentTotalPesoRelativo = 0;
      this.currentStrategicObjective.indicadoresEstrategicos.forEach((i) => {
        if (i.estado === "Presentado") {
          this.currentTotalPesoRelativo += Number(i.pesoRelativo);
        }
        });
      this.validateTotalPeso();
    }

    validateTotalPeso() {
      if (this.currentTotalPesoRelativo > 100) {
        this.validPesoRelativo = "La suma del peso relativo de los indicadores no debe ser mayor a 100%";
      }
    }

    goToTop() {
      (<any>$('html,body')).animate({
      scrollTop: 0},
      500);
    }

    addIndicador() {
      if (!this.currentStrategicObjective.indicadoresEstrategicos) {
        this.currentStrategicObjective.indicadoresEstrategicos = new Array<IndicadorEstrategico>();
      }
      this.currentStrategicObjective.indicadoresEstrategicos.push(<IndicadorEstrategico>{estado: "Incompleto", pesoRelativo: 0});
    }

    removeIndicador(index) {
      this.currentStrategicObjective.indicadoresEstrategicos.splice(index, 1);
      this.getCurrentTotalPesoRelativo();
    }

    editIndicador(indicador) {
      this.currentIndicador = indicador;
      if (angular.element(document.getElementsByTagName('indicadorproject')).length) {
        var indicadorTag = document.getElementsByTagName('indicadorproject');
        angular.element(indicadorTag).remove();
      }
      var referralDivFactory = this.
      $compile(" <indicadorproject currentindicador='formCtrl.currentIndicador' strategicobjetive='formCtrl.currentStrategicObjective' objectiveprojects='formCtrl.allProjects'></indicadorproject> ");
      var referralDiv = referralDivFactory(this.$scope);
      var containerDiv = document.getElementById('indicadorprojectid');
      angular.element(containerDiv).append(referralDiv);
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
