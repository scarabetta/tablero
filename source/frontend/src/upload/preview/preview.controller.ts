import {GeneralServices} from "../../services/services.ts";
import {ProyectoProcesado} from "../../models/jurisdiccion.ts";
const config = require('webpack-config-loader!app-config');

module Preview {

    export class PreviewController {

      private newProjects: ProyectoProcesado[];
      private existentProjects: ProyectoProcesado[];
      private projects: any;
      private submitBtnTxt = 'Continuar';
      private pisarProyectos:boolean;
      private importURL: string;
      private idJurisdiccion: string;
      private idjurisdiccionKey = 'idJurisdiccionStorage';
      private submitBtnDisabled: boolean;

      /*@ngInject*/
      constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private localStorageService:angular.local.storage.ILocalStorageService,
        private $http: ng.IHttpService, private $scope:ng.IScope) {
        this.projects = (<any>$state.params).projects;

        if (!this.projects || this.projects.proyectoProcesados.length === 0) {
          $state.go('upload');
        } else {
          this.newProjects = this.projects.proyectoProcesados.filter((project) => {
            return project.idProyecto === null;
          });
          this.existentProjects = this.projects.proyectoProcesados.filter((project) => {
            return project.idProyecto !== null;
          });
          $scope.$watch(() => { return this.pisarProyectos; }, (value) => {
            this.submitBtnDisabled = false;
            this.submitBtnTxt = this.buildMessage(this.newProjects, this.existentProjects);
            if (this.submitBtnTxt === '') {
              this.submitBtnTxt = 'Inhabilitado';
              this.submitBtnDisabled = true;
            }
          });
          this.pisarProyectos = false;
          // this.submitBtnTxt = this.buildMessage(this.newProjects, this.existentProjects);
          this.importURL = config.authBaseUrl + 'api/importar/proyecto';
          this.idJurisdiccion = JSON.stringify(this.localStorageService.get(this.idjurisdiccionKey));
        }
      }

      submit() {
        this.$http.get(this.importURL + "/" +  this.projects.nombreArchivoError + "/" + this.idJurisdiccion + "/" + this.pisarProyectos)
        .then((response) => {
          console.log(response);
          if (response.status === 200) {
              this.$state.go('results', {'results': response.data});
          }
        })
        .catch((response) => console.log(response.data));
      }

      isEmpty(str) {
        return (str === '');
      };

      buildPartialMessage(arr, msg1, msg2, msg3) {
        return (arr.length === 0) ? '' : (arr.length === 1) ? msg1 + arr.length + msg2 : msg1 + arr.length + msg3;
      };

      buildAppend(msg1, msg2) {
        return (this.isEmpty(msg1) || this.isEmpty(msg2)) ? '' : ' y ';
      };

      buildMessage(arr1, arr2) {
        var msg1 = this.buildPartialMessage(arr1, 'Importar ', ' proyecto nuevo', ' proyectos nuevos');
        var msg2 = this.pisarProyectos ? this.buildPartialMessage(arr2, 'actualizar ', ' existente', ' existentes') : '';
        var andMsg = this.buildAppend(msg1, msg2);
        return msg1 + andMsg + msg2;
      };

    }

}

export = Preview;
