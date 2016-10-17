import {GeneralServices} from "../services/services.ts";

module Priorization {

    export class PriorizationController {

      private dataResult: any;
      private results: any;

      /*@ngInject*/
      constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private $scope:ng.IScope, private $compile: ng.ICompileService) {
        this.results = (<any>$state.params).results;
        console.log($state.params);
        console.log(this.results);
        this.showNotifications();
        services.getValuesPriorization().then((data) => {
          this.dataResult = data;
        });
      }

      saveData = (function () {
        var a = document.createElement("a");
        document.body.appendChild(a);
        return function (data, fileName) {
            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
            var url = window.URL.createObjectURL(blob);
            a.href = url;
            (<any>a).download = fileName;
            a.click();
            window.URL.revokeObjectURL(url);
        };
      }());

      downloadExcelPriorization() {
        this.services.downloadExcelPriorization().then((data) => {
           this.saveData(data, 'PIG_Priorizacion.xlsx');
           this.$state.reload();
        });
      }

      cancelPriorization() {
        this.services.cancelPriorization().then((data) => {
           this.$state.reload();
        });
      }

      addNotification(data) {
        var formDiv = document.getElementsByTagName('alert');
        angular.element(formDiv).remove();
        var referralDivFactory = this.$compile(' <notification type="' + data.type + '" icon="' + data.icon + '" title="' + data.title + '" text="' + data.text + '" ' + data.action + '="'  + data.valueAction + '" textlink="' + data.textlink + '" callback="' + 'homeCtrl.' + data.callback + '"></notification> '); // tslint:disable-line max-line-length
        var referralDiv = referralDivFactory(this.$scope);
        var containerDiv = document.getElementById('notifications');
        angular.element(containerDiv).append(referralDiv);
      }

      showNotifications() {
        if (this.results) {
          var approvedProjects = 0;
          var projectsWithErrors = 0;
          this.results.proyectoProcesados.forEach((p) => {
            if (p.mensajeErrores && p.mensajeErrores.length > 0) {
              projectsWithErrors++;
              console.log(projectsWithErrors);
            }
          });
          if (this.results.erroresDeSolapa.length > 0 || projectsWithErrors > 0 || this.results.errorGenerico) {
            console.log('Hay errores');
            this.showErrorNotification();
          }
          console.log('into showNotifications');
          this.results.proyectoProcesados.forEach((p) => {
            if (p.estado === "Pre Aprobado") {
              approvedProjects++;
            }
          });
          if (approvedProjects > 0 && this.results.importeAprobado) {
            console.log('No hay errores');
            this.showOkNotification(approvedProjects, this.results.importeAprobado);
          }
        }
      }

      showOkNotification(projects, totalBudget) {
        var notificationDataCompleto = {
          "type" : "success",
          "icon" : "ok-sign",
          "title" : "Ok",
          "text" : "La priorización se importó con éxito. Se aprobaron " + projects + " proyectos con un presupuesto nuevo de $" + totalBudget, // tslint:disable-line max-line-length
        };
        this.addNotification(notificationDataCompleto);
      }

      showErrorNotification() {
        var errores = "Se detectaron errores en los proyectos a importar. Los proyectos con errores se volvieron a su estado anterior. Iniciá la priorización nuevamente teniendo en cuenta los siguientes errores: " + "<br><ul>"; // tslint:disable-line max-line-length
        this.results.erroresDeSolapa.forEach((e) => {
          errores = errores + "<li>" + e + "</li>";
        });
        this.results.proyectoProcesados.forEach((p) => {
          if (p.mensajeErrores && p.mensajeErrores.length > 0) {
            p.mensajeErrores.forEach((e) => {
              errores = errores + "<li>" + "Id de Proyecto: " + p.idProyecto + ". " + e + "</li>";
            });
          }
        });
        errores = errores + "</ul>";
        console.log(errores);
        var notificationDataIncompleto = {
          "type" : "warning",
          "icon" : "exclamation-sign",
          "title" : "Error",
          "text" : errores // tslint:disable-line
        };
        this.addNotification(notificationDataIncompleto);
      }

    }
}

export = Priorization;
