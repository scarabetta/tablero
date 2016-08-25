/**
 * Created by enocmontiel on 7/26/16.
 */
import {Jurisdiccion} from "../models/jurisdiccion.ts";
import {GeneralServices} from "../services/services.ts";
const template = require('./jurisdiccion-header.html');

module Home {

  export class HeaderController {

    private jurisdiccion:Jurisdiccion[];
    private idjurisdiccionKey = 'idJurisdiccionStorage';

    constructor(private services:GeneralServices, private localStorageService:angular.local.storage.ILocalStorageService) {
      var idJurisdiccionStorage = this.localStorageService.get(this.idjurisdiccionKey);
      if (idJurisdiccionStorage) {
        services.getJurisdiccion(idJurisdiccionStorage).then((data) => this.jurisdiccion = data);
      }
    }

  }

  export let jurisdiccionHeaderComponent =  {
      templateUrl: template,
      controller: HeaderController
  };

}

export = Home;
