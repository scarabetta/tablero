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
import {Obra} from "../models/jurisdiccion";
import {TipoObra} from "../models/jurisdiccion";
import {ObjetivoJurisdiccional} from "../models/jurisdiccion";
import {Usuario} from "../models/jurisdiccion";
import {Rol} from "../models/jurisdiccion";
import {TemaTransversal} from "../models/jurisdiccion";
import {Etiquetas} from "../models/jurisdiccion";

module Services {

    export class GeneralServices {

        private tokenKey = 'token';
        private idjurisdiccionKey = 'idJurisdiccionStorage';
        private apiBaseUrl = config.authBaseUrl + 'api/';

        /*@ngInject*/
        constructor(private $http: ng.IHttpService, private $state: ng.ui.IStateService, private localStorageService:angular.local.storage.ILocalStorageService) {}

        jurisdicciones(): ng.IPromise<any> {
            return this.$http.get<Jurisdiccion>(this.apiBaseUrl + "jurisdiccion/resumen")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getJurisdiccion(id): ng.IPromise<any> {
            return this.$http.get<Jurisdiccion>(this.apiBaseUrl + "jurisdiccion/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        editJurisdiccion(jurisdiccion): ng.IPromise<any> {
            return this.$http.put(this.apiBaseUrl + "jurisdiccion/", jurisdiccion)
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

        etiquetas(idproyecto): ng.IPromise<any> {
            return this.$http.get<Etiquetas>(this.apiBaseUrl + "etiqueta/" + idproyecto)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        etiquetar(etiquetas, idproyecto): ng.IPromise<any> {
            return this.$http.post<Etiquetas>(this.apiBaseUrl + "proyecto/etiquetar/" + idproyecto, etiquetas)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        downloadExcelMAestro(): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "importar/download/template/proyecto", {responseType:'arraybuffer'})
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        downloadAllProjects(): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "exportar/proyectos/reporte", {responseType:'arraybuffer'})
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

        presentProject(project): ng.IPromise<any> {
            return this.$http.post(this.apiBaseUrl + "proyecto/presentar", project)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        presentAllProject(idJurisdiccion): ng.IPromise<any> {
            return this.$http.post(this.apiBaseUrl + "jurisdiccion/presentarCompletos/" + idJurisdiccion, '')
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

        presentProjectDetail(project): ng.IPromise<any> {
            project.fechaInicio = new Date(project.fechaInicio);
            project.fechaFin = new Date(project.fechaFin);
            return this.$http.post(this.apiBaseUrl + "proyecto/presentarDetalle", project)
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

        getMoveOptions(idProject): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "proyecto/cambiarEstado/accionesPermitidas/" + idProject)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        changeState(state, project): ng.IPromise<any> {
            return this.$http.post(this.apiBaseUrl + "proyecto/cambiarEstado/" + state, project)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getPrioridadesJefatura(): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "proyecto/prioridadesJefatura/")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getValuesPriorization(): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "exportar/resumenProyectos")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        downloadExcelPriorization(): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "exportar/proyectos", {responseType:'arraybuffer'})
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        cancelPriorization(): ng.IPromise<any> {
            return this.$http.get(this.apiBaseUrl + "exportar/cancelarPriorizacion")
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

        getUsers(): ng.IPromise<any> {
            return this.$http.get<Usuario>(this.apiBaseUrl + "usuario/resumen")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getUserByToken(): ng.IPromise<any> {
            return this.$http.get<Usuario>(this.apiBaseUrl + "usuario/porToken/")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getUser(id): ng.IPromise<any> {
            return this.$http.get<Usuario>(this.apiBaseUrl + "usuario/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        presaveUser(user) {
          if (user.jurisdicciones) {
            user.jurisdicciones = user.jurisdicciones.map((entry) => {
              return {"idJurisdiccion": entry.idJurisdiccion};
            });
          }
          if (user.roles) {
            user.roles = user.roles.map((entry) => {
              return {"idRol": entry.idRol};
            });
          }
        }

        saveUser(user): ng.IPromise<any> {
            this.presaveUser(user);
            return this.$http.post(this.apiBaseUrl + "usuario/", user)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        updateUser(user): ng.IPromise<any> {
            this.presaveUser(user);
            return this.$http.put(this.apiBaseUrl + "usuario/", user)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        toggleUserState(user): ng.IPromise<any> {
            this.presaveUser(user);
            return this.$http.put(this.apiBaseUrl + "usuario/", user)
                .then((response) => response.data)
                .catch((response) => {
                  user.activo = !user.activo;
                  console.log(response.data);
                });
        }

        deleteUser(id): ng.IPromise<any> {
            return this.$http.delete(this.apiBaseUrl + "usuario/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getRoles(): ng.IPromise<any> {
          return this.$http.get<Rol>(this.apiBaseUrl + "rol/")
          .then((response) => response.data)
          .catch((response) => console.log(response.data));
        }

        getTemasTransversales(): ng.IPromise<any> {
          return this.$http.get<TemaTransversal>(this.apiBaseUrl + "temaTransversal/")
          .then((response) => response.data)
          .catch((response) => console.log(response.data));
        }

        getTemaTransversal(id): ng.IPromise<any> {
            return this.$http.get<TemaTransversal>(this.apiBaseUrl + "temaTransversal/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        saveTemaTransversal(temaTransversal): ng.IPromise<any> {
            return this.$http.post(this.apiBaseUrl + "temaTransversal/", temaTransversal)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        updateTemaTransversal(temaTransversal): ng.IPromise<any> {
            return this.$http.put(this.apiBaseUrl + "temaTransversal/", temaTransversal)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        deleteTemaTransversal(id): ng.IPromise<any> {
            return this.$http.delete(this.apiBaseUrl + "temaTransversal/" + id)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        toggleCrossTopicState(crossTopic): ng.IPromise<any> {
            return this.$http.put(this.apiBaseUrl + "temaTransversal/", crossTopic)
                .then((response) => response.data)
                .catch((response) => {
                  crossTopic.activo = !crossTopic.activo;
                  console.log(response.data);
                });
        }

        formProjectFileUploader(file, idProject, idJurisdiccion): ng.IPromise<any> {
          return this.$http({
                    method: 'POST',
                    url: this.apiBaseUrl + 'proyecto/subir_archivo',
                    headers: {
                        'Content-Type': undefined
                    },
                    data: {
                        archivoASubir: file,
                        id: idProject,
                        idJurisdiccion: idJurisdiccion
                    },
                    transformRequest: function (data, headersGetter) {
                        var formData = new FormData();
                        angular.forEach(data, function (value, key) {
                            formData.append(key, value);
                        });
                        return formData;
                    }
                })
              .then((response) => response.data)
              .catch((response) => console.log(response.data));
        }

        getProjectFile(nombreArchivo, id, idJurisdiccion): ng.IPromise<any> {
          return this.$http.get(this.apiBaseUrl + "proyecto/bajar_archivo/" + id + '/' + idJurisdiccion + '/' + nombreArchivo, {responseType: 'blob'})
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
          this.localStorageService.remove(this.idjurisdiccionKey);
          this.$state.go('login');
        }

        tiposDeObra(): ng.IPromise<any> {
            return this.$http.get<TipoObra>(this.apiBaseUrl + "tipoObra/")
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }

        getObra(idObra): ng.IPromise<any> {
            return this.$http.get<Obra>(this.apiBaseUrl + "obra/" + idObra)
                .then((response) => response.data)
                .catch((response) => console.log(response.data));
        }
    }

}

export = Services;
