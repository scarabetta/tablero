import {GeneralServices} from "../services/services.ts";

module Priorization {

    export class PriorizationController {

      private dataResult: any;

      /*@ngInject*/
      constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private $scope:ng.IScope, private $compile: ng.ICompileService) {
        services.getValuesPriorization().then((data) => {
          console.log(data);
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
           this.saveData(data, 'PGI_Priorizacion.xlsx');
        });
      }

    }
}

export = Priorization;
