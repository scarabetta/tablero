import {GeneralServices} from "../services/services.ts";
import {ObjetivoOperativo} from "../models/jurisdiccion";
const template = require('./form-operative-objective.html');

module Home {

  export class FormOperativeObjectiveComponentController {
    private title: string;
    private idobjetivoestrategico: number;
    private idoperativeobjective: number;
    private currentOperativeObjective: ObjetivoOperativo;

    /*@ngInject*/
    constructor(private services:GeneralServices, private $http: ng.IHttpService, private $state:ng.ui.IStateService,
      private $compile: ng.ICompileService, private $scope:ng.IScope) {
      if (this.idoperativeobjective) {
        this.title = "Modificar objetivo operativo";
        services.getOperativeObjective(this.idoperativeobjective).then((data) => {
          this.currentOperativeObjective = data;
        });
      } else {
       this.title = "Nuevo objetivo operativo";
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

    deleteOperativeObjectiveByID(id) {
        if (this.currentOperativeObjective.proyectos.length > 0) {
          var notificationData = {
            "type" : "warning",
            "icon" : "exclamation-sign",
            "title" : "Alerta",
            "text" : "No puede borrar el Objetivo Operativo si posee Proyectos.",
            "action": "gotooperativo",
            "valueAction" : true,
            "textlink": "Ir al formulario"
          };
          this.addNotification(notificationData);
        } else {
            this.services.deleteOperativeObjective(id).then((data) => {
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

    goToTop() {
      (<any>$('html,body')).animate({
      scrollTop: 0},
      500);
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
