import {GeneralServices} from "../services/services.ts";
const template = require('./form-labels.html');
import {Etiquetas} from "../models/jurisdiccion";

module Home {

  export class FormLabelsComponentController {
    private idproyecto: number;
    private etiquetasData: Etiquetas;
    private etiquetasDataToSave: Etiquetas;
    private etiquetasFromProject: Etiquetas;

    /* tslint:disable:no-unused-variable */
    private ttPlaceholder = "Etiquetas de temas transversales";
    private cpPlaceholder = "Etiquetas de compromiso público";
    private othersPlaceholder = "Otras etiquetas";

    /*@ngInject*/
    constructor(private services:GeneralServices, private $http: ng.IHttpService, private $state:ng.ui.IStateService,
      private $compile: ng.ICompileService, private $scope:ng.IScope) {

        services.etiquetas(this.idproyecto).then((data) => {
          this.etiquetasDataToSave = data;
        });
        services.etiquetas('').then((data) => {
          this.etiquetasData = data;
        });
    }

    loadOthers($query) {
      return this.etiquetasData.otrasEtiquetas.filter(function(tag) {
        return tag.etiqueta.toLowerCase().indexOf($query.toLowerCase()) !== -1;
      });
    }
    loadTT($query) {
      return this.etiquetasData.temasTransversales.filter(function(tag) {
        return tag.temaTransversal.toLowerCase().indexOf($query.toLowerCase()) !== -1;
      });
    }
    loadCP($query) {
      return this.etiquetasData.compromisosPublicos.filter(function(tag) {
        return tag.compromisoPublico.toLowerCase().indexOf($query.toLowerCase()) !== -1;
      });
    }
    saveLabels() {
      if (this.etiquetasDataToSave.temasTransversales === null && this.etiquetasDataToSave.compromisosPublicos === null && this.etiquetasDataToSave.otrasEtiquetas === null) {
        this.etiquetasDataToSave.temasTransversales = [];
        this.etiquetasDataToSave.compromisosPublicos = [];
        this.etiquetasDataToSave.otrasEtiquetas = [];
      }
      this.services.etiquetar(this.etiquetasDataToSave, this.idproyecto).then((data) => {
        this.cancelLabels();
      });
    }

    cancelLabels() {
      var formDiv = document.getElementsByTagName('formlabels');
      angular.element(formDiv).remove();
    }

    removeLabelTT(label) {
      for (var i = 0; i < this.etiquetasFromProject.temasTransversales.length; i++) {
        if (this.etiquetasFromProject.temasTransversales[i].temaTransversal === label.temaTransversal) {
          this.etiquetasFromProject.temasTransversales.splice( i , 1 );
        }
      }
    }

    removeLabelCP(label) {
      for (var i = 0; i < this.etiquetasFromProject.compromisosPublicos.length; i++) {
        if (this.etiquetasFromProject.compromisosPublicos[i].compromisoPublico === label.compromisoPublico) {
          this.etiquetasFromProject.compromisosPublicos.splice( i , 1 );
        }
      }
    }

    removeLabelOthers(label) {
      for (var i = 0; i < this.etiquetasFromProject.otrasEtiquetas.length; i++) {
        if (this.etiquetasFromProject.otrasEtiquetas[i].etiqueta === label.etiqueta) {
          this.etiquetasFromProject.otrasEtiquetas.splice( i , 1 );
        }
      }
    }

    goToTop() {
      (<any>$('html,body')).animate({
      scrollTop: 0},
      500);
    }
  }

  export let formLabelsComponent = {
      bindings: {
          idproyecto: '='
      },
      templateUrl: template,
      controller: FormLabelsComponentController,
      controllerAs: 'formCtrl',
  };
}

export = Home;
