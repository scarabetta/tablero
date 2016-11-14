import {GeneralServices} from "../services/services.ts";
const config = require('webpack-config-loader!app-config');

module Upload {

    export class UploadController {

      private importURL:string;
      private idJurisdiccion: string;
      private idjurisdiccionKey = 'idJurisdiccionStorage';

        /*@ngInject*/
        constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private localStorageService:angular.local.storage.ILocalStorageService,
          private $compile: ng.ICompileService, private $scope:ng.IScope, private $http: ng.IHttpService) {
          this.importURL = config.authBaseUrl + 'api/importar/preview';
          this.idJurisdiccion = JSON.stringify(this.localStorageService.get(this.idjurisdiccionKey));
        }

        submit() {
          var f = document.getElementById('archivoAImportar');
          var file = (<any>f).files[0];
          var fd = new FormData();
          fd.append('archivoAImportar', file);
          this.$http.post(this.importURL + "/" + this.idJurisdiccion, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
          })
          .then((response) => {
            if (response.status === 200) {
                this.$state.go('preview', {'projects': response.data});
            }
          })
          .catch((response) => console.log(response.data));
        }

    }
}

export = Upload;
