import {GeneralServices} from "../../services/services.ts";
const config = require('webpack-config-loader!app-config');

module Upload {

    export class UploadController {

      private importURL:string;
      private idJurisdiccion: string;
      private idjurisdiccionKey = 'idJurisdiccionStorage';
      private nombreArchivoError: string;

        /*@ngInject*/
        constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private localStorageService:angular.local.storage.ILocalStorageService,
          private $compile: ng.ICompileService, private $scope:ng.IScope, private $http: ng.IHttpService) {
          console.log('upload');
          this.importURL = config.authBaseUrl + 'api/importar/proyecto/priorizado';
          this.idJurisdiccion = JSON.stringify(this.localStorageService.get(this.idjurisdiccionKey));
        }

        submit() {
          var f = document.getElementById('archivoAImportar');
          var file = (<any>f).files[0];
          var fd = new FormData();
          fd.append('archivoAImportar', file);
          this.$http.post(this.importURL + "/preview/" + this.idJurisdiccion, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
          })
          .then((response) => {
            console.log(response);
            this.nombreArchivoError = (<any>response.data).nombreArchivoError;
            this.importProjects();
          })
          .catch((response) => console.log(response.data));
        }

        importProjects() {
          this.$http.get(this.importURL + "/" +  this.nombreArchivoError + "/" + this.idJurisdiccion)
          .then((response) => {
            console.log(response);
            if (response.status === 200) {
                this.$state.go('priorization', {'results': response.data});
            }
          })
          .catch((response) => console.log(response.data));
        }

    }
}

export = Upload;
