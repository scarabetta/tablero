import {GeneralServices} from "../../services/services.ts";
import {ProyectoProcesado} from "../../models/jurisdiccion.ts";
const config = require('webpack-config-loader!app-config');

module Results {

    export class ResultsController {

      private results: any;
      private completedProjects: ProyectoProcesado[];
      private incompletedProjects: ProyectoProcesado[];
      private wrongProjects: ProyectoProcesado[];
      private errorURL: string;
      private idJurisdiccion: string;
      private idjurisdiccionKey = 'idJurisdiccionStorage';

      /*@ngInject*/
      constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private $http: ng.IHttpService, private localStorageService:angular.local.storage.ILocalStorageService) {
          this.results = (<any>$state.params).results;

          if (!this.results) {
            $state.go('upload');
          } else {
            this.wrongProjects = this.results.proyectoProcesados.filter((project) => {
              return project.mensajeErrores;
            });
            this.completedProjects = this.results.proyectoProcesados.filter((project) => {
              return (project.estado === "Completo");
            });
            this.incompletedProjects = this.results.proyectoProcesados.filter((project) => {
              return (project.estado === "Incompleto");
            });

            console.log(this.completedProjects);
            console.log(this.incompletedProjects);
            console.log(this.wrongProjects);

            this.errorURL = config.authBaseUrl + 'api/importar/download/error';
            console.log(this.errorURL);
            this.idJurisdiccion = JSON.stringify(this.localStorageService.get(this.idjurisdiccionKey));
          }

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

      downloadErrorsFile() {
        console.log('Descargando archivo');

        this.$http.get(this.errorURL + "/" + this.results.nombreArchivoError + "/" + this.idJurisdiccion, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined},
          responseType:'arraybuffer'
        }).then((response) => {
          console.log(response);
           var addZero = function(i) {
             if (i < 10) {
                 i = "0" + i;
             }
             return i;
           };
           var date = new Date();
           var year = addZero(date.getFullYear());
           var month = addZero(date.getMonth());
           var day = addZero(date.getDate());
           var hour = addZero(date.getHours());
           var minutes = addZero(date.getMinutes());
           var fileName = 'PGI_ProyectosNoImportados_' + year + month + day
                           + '_' + hour + minutes + '.xlsx';
           this.saveData(response.data, fileName);
        })
        .catch((response) => console.log(response.data));

      }
  }
}

export = Results;
