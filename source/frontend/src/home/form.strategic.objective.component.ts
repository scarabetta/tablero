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

    deleteStrategicObjective(id) {
      var dataAlert = {
        title: "Aviso",
        text: "Se va a eliminar el Objetivo Estratégico. ¿Continuar?",
        callback: 'deleteStrategicObjectiveById',
        id: id
      };
      this.addAlert(dataAlert);
    }

    addNotification(data) {
      var referralDivFactory = this.$compile(' <notification type="' +data.type+ '" icon="' +data.icon+ '" title="' +data.title+ '" text="' +data.text+ '" '+data.action+'="' +data.valueAction+ '" textlink="' +data.textlink+ '"></notification> '); // tslint:disable-line
      var referralDiv = referralDivFactory(this.$scope);
      var containerDiv = document.getElementById('notifications');
      angular.element(containerDiv).append(referralDiv);
      this.goToTop();
    }

    goToTop() {
      (<any>$('html,body')).animate({
      scrollTop: 0},
      500);
    }

    addAlert(data) {
      var formDiv = document.getElementsByTagName('alertmodal');
      angular.element(formDiv).remove();
      var referralDivFactory = this.$compile(" <alertmodal title='" + data.title + "' text='" + data.text + "' callback='formCtrl." + data.callback + "(" + data.id + ")'></alertmodal> ");
      var referralDiv = referralDivFactory(this.$scope);
      var containerDiv = document.getElementById('grupo-level-3-3');
      angular.element(containerDiv).append(referralDiv);
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
