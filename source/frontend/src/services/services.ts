/**
 * Created by enocmontiel on 7/11/16.
 */
const config = require('webpack-config-loader!app-config');
import {Jurisdiccion} from "../models/jurisdiccion";
import {PoblacionMeta} from "../models/jurisdiccion";
import {EjeDeGobierno} from "../models/jurisdiccion";
import {Comuna} from "../models/jurisdiccion";
import {Proyecto} from "../models/jurisdiccion";
import {ObjetivoOperativo} from "../models/jurisdiccion";
import {ObjetivoJurisdiccional} from "../models/jurisdiccion";

module Services {

    export class GeneralServices {

        private tokenKey = 'token';
        private apiBaseUrl = config.authBaseUrl + 'api/';

        /*@ngInject*/
        constructor(private $http: ng.IHttpService, private $state: ng.ui.IStateService, private localStorageService:angular.local.storage.ILocalStorageService) {}

        jurisdicciones(): ng.IPromise<any> {
            return this.$http.get<Jurisdiccion>(this.apiBaseUrl + "jurisdiccion/")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getJurisdiccion(id): ng.IPromise<any> {
            return this.$http.get<Jurisdiccion>(this.apiBaseUrl + "jurisdiccion/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        poblacionMeta(): ng.IPromise<any> {
            return this.$http.get<PoblacionMeta>(this.apiBaseUrl + "poblacionMeta/")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        ejesDeGobierno(): ng.IPromise<any> {
            return this.$http.get<EjeDeGobierno>(this.apiBaseUrl + "ejeDeGobierno/")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        comunas(): ng.IPromise<any> {
            return this.$http.get<Comuna>(this.apiBaseUrl + "comuna/")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        downloadExcelMAestro(): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "importar/download/template/proyecto", {responseType:'arraybuffer'})
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        saveProject(project): ng.IPromise<any> {
            project.fechaInicio = new Date(project.fechaInicio);
            project.fechaFin = new Date(project.fechaFin);
            return this.$http.post(this.apiBaseUrl + "proyecto/", project)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        updateProject(project): ng.IPromise<any> {
            project.fechaInicio = new Date(project.fechaInicio);
            project.fechaFin = new Date(project.fechaFin);
            return this.$http.put(this.apiBaseUrl + "proyecto/", project)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        deleteProject(id): ng.IPromise<any> {
            return this.$http.delete(this.apiBaseUrl + "proyecto/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        saveOperativeObjective(opObj): ng.IPromise<any> {
            return this.$http.post(this.apiBaseUrl + "objetivoOperativo/", opObj)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        updateOperativeObjective(opObj): ng.IPromise<any> {
            return this.$http.put(this.apiBaseUrl + "objetivoOperativo/", opObj)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        deleteOperativeObjective(id): ng.IPromise<any> {
            return this.$http.delete(this.apiBaseUrl + "objetivoOperativo/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        saveStrategicObjective(opEst): ng.IPromise<any> {
            return this.$http.post(this.apiBaseUrl + "objetivoJurisdiccional/", opEst)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        updateStrategicObjective(opEst): ng.IPromise<any> {
            return this.$http.put(this.apiBaseUrl + "objetivoJurisdiccional/", opEst)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        deleteStrategicObjective(id): ng.IPromise<any> {
            return this.$http.delete(this.apiBaseUrl + "objetivoJurisdiccional/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getProject(idProject): ng.IPromise<any> {
            return this.$http.get<Proyecto>(this.apiBaseUrl + "proyecto/" + idProject)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getOperativeObjective(idOperativeObjective): ng.IPromise<any> {
            return this.$http.get<ObjetivoOperativo>(this.apiBaseUrl + "objetivoOperativo/" + idOperativeObjective)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getStrategicObjective(idStrategicObjective): ng.IPromise<any> {
            return this.$http.get<ObjetivoJurisdiccional>(this.apiBaseUrl + "objetivoJurisdiccional/" + idStrategicObjective)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        serviceVersion(): ng.IPromise<any> {
            return this.$http.get<string>(this.apiBaseUrl + "version")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        login(userData): ng.IPromise<any> {
          return this.$http.post<string>(config.authBaseUrl + 'authentication/login', userData)
              .then((response) => response.data)
              .catch((response) => console.log(response.data));
        }

        logout() {
          this.localStorageService.remove(this.tokenKey);
          this.$state.go('login');
        }
    }

}

export = Services;
