import {GeneralServices} from "../services/services.ts";
const template = require('./excel.html');
const templateButton = require('./excel-button.html');

module App {

  export class ExcelController {

    /*@ngInject*/
    constructor(private services:GeneralServices) {}

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

    downloadExcel() {
       this.services.downloadExcelMAestro().then((data) => {
          this.saveData(data, 'PIG_ExcelMaestro.xlsx');
       });
    }

    downloadAllProjects() {
       this.services.downloadAllProjects().then((data) => {
          this.saveData(data, 'PIG_Proyectos.xlsx');
       });
    }
  }

  export let excelComponent = {
      bindings: {
          showimport: '<'
      },
      templateUrl: template,
      controller: ExcelController,
      controllerAs: 'excelCtrl',
  };

  export let excelButton = {
      templateUrl: templateButton,
      controller: ExcelController,
      controllerAs: 'excelCtrl',
  };
}

export = App;
