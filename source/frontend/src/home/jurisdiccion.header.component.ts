/**
 * Created by enocmontiel on 7/26/16.
 */
import {Jurisdiccion} from "../models/jurisdiccion.ts";
import {GeneralServices} from "../services/services.ts";
const template = require('./jurisdiccion-header.html');

module Home {

  export class HeaderController {

    private jurisdiccion:Jurisdiccion;
    private idjurisdiccionKey = 'idJurisdiccionStorage';
    private modelJurisdiccion: any;

    constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private $scope:ng.IScope,
      private localStorageService:angular.local.storage.ILocalStorageService, private $compile: ng.ICompileService) {
      var idJurisdiccionStorage = this.localStorageService.get(this.idjurisdiccionKey);
      if (idJurisdiccionStorage) {
        services.getJurisdiccion(idJurisdiccionStorage).then((data) => {
          this.jurisdiccion = data;
          this.modelJurisdiccion = {
            idJurisdiccion : data.idJurisdiccion,
            nombre : data.nombre,
            abreviatura : data.abreviatura,
            mision : data.mision,
            codigo : data.codigo
          };
        });
      }

    }

    editJurisdiccion() {
      var scope = this;
      (<any>$("#mision")).hide();
      (<any>$("#inputMisionArea")).show();
      (<any>$("#inputMisionArea")).append('<textarea id="areaMision" class="mision inputMision"></textarea><br>');
      (<any>$("#areaMision")).val(this.jurisdiccion.mision);
      (<any>$("#areaMision")).focus();
      (<any>$("#inputMisionArea"))
      .append('<button class="btn btn-default" id="saveMision">Guardar</button>&nbsp;&nbsp;'); // tslint:disable-line max-line-length
      (<any>$("#inputMisionArea"))
      .append('<button class="btn btn-default" id="cancelEdit">Cancelar</button>');
      (<any>$("#cancelEdit")).click(function() {
        (<any>$("#inputMisionArea *")).remove();
        (<any>$("#mision")).show();
      });
      (<any>$("#saveMision")).click(function() {
        scope.modelJurisdiccion.mision = (<any>$("#areaMision")).val();
        scope.services.editJurisdiccion(scope.modelJurisdiccion).then((data) => {
          scope.$state.reload();
        });
      });
    }

  }

  export let jurisdiccionHeaderComponent =  {
      templateUrl: template,
      controller: HeaderController
  };

}

export = Home;



