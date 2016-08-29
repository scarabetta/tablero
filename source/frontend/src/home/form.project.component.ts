/**
 * Created by enocmontiel on 7/19/16.
 */
import {GeneralServices} from "../services/services.ts";
import {Proyecto} from "../models/jurisdiccion";
import {PoblacionMeta} from "../models/jurisdiccion.ts";
import {EjeDeGobierno} from "../models/jurisdiccion.ts";
import {Comuna} from "../models/jurisdiccion.ts";
import {Presupuesto} from "../models/jurisdiccion.ts";
import {Jurisdiccion} from "../models/jurisdiccion.ts";
const template = require('./form-project.html');

module Home {

    export class FormProjectComponentController {

      private currentProject:Proyecto;
      private poblacionesMeta:PoblacionMeta[];
      private ejesDeGobierno:EjeDeGobierno[];
      private totalBudget: number;
      private comunas:Comuna[];
      private idobjetivo: number;
      private idproject: number;
      private allInputs: boolean;
      private jurisdiccion: Jurisdiccion;
      private idjurisdiccionKey = 'idJurisdiccionStorage';
      private countForm: string;
      private percentForm: number;
      private validDate: string;
      private dirNoValidate: string;
      private noDir:boolean;
      private datePickerInicio = {
        status: false
      };
      private datePickerFin = {
        status: false
      };

      /* tslint:disable */
      private configDropDown = {
          selectAll       : "Seleccionar todas",
          selectNone      : "Remover todas",
          search          : "Buscar comunas...",
          nothingSelected : "No se ha seleccionado ninguna"
      };
      /* tslint:enable */

      /*@ngInject*/
      constructor(private services:GeneralServices, private $http: ng.IHttpService, private $state:ng.ui.IStateService, private $scope:ng.IScope,
        private localStorageService:angular.local.storage.ILocalStorageService, private $compile: ng.ICompileService) {
          services.poblacionMeta().then((data) => this.poblacionesMeta = data);
          services.ejesDeGobierno().then((data) => this.ejesDeGobierno = data);
          var idJurisdiccionStorage = this.localStorageService.get(this.idjurisdiccionKey);
          if (idJurisdiccionStorage) {
            services.getJurisdiccion(idJurisdiccionStorage).then((data) => {
              this.jurisdiccion = data;
            });
          }
          this.currentProject = <Proyecto>{
                "idProyecto": null,
                "nombre": null,
                "codigo": "a definir",
                "descripcion": null,
                "tipoProyecto": null,
                "meta": null,
                "unidadMeta": null,
                "poblacionAfectada": null,
                "liderProyecto": null,
                "area": null,
                "tipoUbicacionGeografica": null,
                "direccion": null,
                "cambioLegislativo": null,
                "fechaInicio": null,
                "fechaFin": null,
                "prioridadJurisdiccional": null,
                "estado": null,
                "idObjetivoOperativo2": null,
                "presupuestosPorAnio": null,
                "ejesDeGobierno": null,
                "poblacionesMeta":null,
                "comunas": null
              };

          delete this.currentProject.idProyecto;

          if (this.idproject) {
            services.getProject(this.idproject).then((data) => {
              if (data.comunas.length === 0) {
                this.noDir = false;
                this.dirNoValidate = 'comunas';
              } else if (data.direccion === "") {
                this.noDir = false;
                this.dirNoValidate = 'direccion';
              } else {
                this.dirNoValidate = '';
                this.noDir = true;
              }
              data.fechaInicio = new Date(data.fechaInicio);
              data.fechaFin = new Date(data.fechaFin);
              this.currentProject = data;
              services.comunas().then((data) => {
                this.setTickedProperty(data, false);
                this.comunas = data;
                if (this.currentProject.comunas.length > 0) {
                  this.currentProject.comunas.forEach((entry) => {
                    this.comunas.forEach((entryComuna) => {
                      if (entry.idComuna === entryComuna.idComuna) {
                        this.setSingleTickedTrue(entryComuna);
                      }
                    });
                  });
                }
                this.getTotalBudget();
              });
            });
          } else {
              services.comunas().then((data) => {
                this.setTickedProperty(data, false);
                this.comunas = data;
              });
              this.currentProject.idObjetivoOperativo2 = this.idobjetivo;
              this.allInputs = false;
          }

          /* MARTIN HIZO ESTE CODIGO :)  POR FAVOR REFACTORIZAR, git no digas que fui yo que fue martin */

          $scope.$watch('formCtrl.currentProject', (newVal, oldVal) => {
              var errors = 0;
              for (var e in this.currentProject) {
                if ((e !== 'idObjetivoJurisdiccional2' && e !== 'coordenadaX' && e !== 'coordenadaY' && e !== 'idJurisdiccion2' && e !== 'organismosCorresponsables' && e !== 'codigo' && e !== 'archivos') && (this.currentProject[e] === null || this.currentProject[e] === "" || this.currentProject[e] === undefined || this.currentProject[e].length === 0)) {// tslint:disable-line
                  if (e !== this.dirNoValidate) {
                    if (e === "dirección" || e === "Comunas" && !this.noDir) {
                      errors++;
                    }
                  }
                }
              }
              if (errors === 0) {
                  this.allInputs = true;
                  this.countForm = "Has ingresado todos los campos.";
              } else {
                this.allInputs = false;
                this.percentForm = Math.round((18 - errors) * (100 / 18));
                this.countForm = "Te quedan " + errors + " datos por ingresar";
              }
          }, true);
      };

      presentProject() {
        var scope = this;
          this.services.presentProject(this.currentProject).then((data) => {
              scope.$state.reload().then(function() {
                  var notificationData = {
                    "type" : "success",
                    "icon" : "ok-sign",
                    "title" : "Ok",
                    "text" : "El proyecto se presento con éxito." // tslint:disable-line
                  };
                  scope.addNotification(notificationData);
              });
          });
      }

      saveProject() {
        var scope = this;
        if (this.idproject) {
          this.services.updateProject(this.currentProject).then((data) => {
              this.$state.reload();
           });
        } else {
            this.services.saveProject(this.currentProject).then((data) => {
              if (data.idProyecto) {
                scope.$state.reload().then(function() {
                    var notificationData = {
                      "type" : "success",
                      "icon" : "ok-sign",
                      "title" : "Ok",
                      "text" : "El proyecto se creó con éxito como borrador." // tslint:disable-line
                    };
                    scope.addNotification(notificationData);
                });
              }
            });
        }
      }

      addAlert(data) {
        var formDiv = document.getElementsByTagName('alertmodal');
        angular.element(formDiv).remove();
        var referralDivFactory = this.$compile(" <alertmodal title='" + data.title + "' text='" + data.text + "' callback='formCtrl." + data.callback + "(" + data.id + ")'></alertmodal> ");
        var referralDiv = referralDivFactory(this.$scope);
        var containerDiv = document.getElementById('alertmodalcomponent');
        angular.element(containerDiv).append(referralDiv);
      }

      deleteProject(id) {
        var dataAlert = {
          title: "Aviso",
          text: "Se va a eliminar el Proyecto. ¿Continuar?",
          callback: 'deleteProjectById',
          id: id
        };
        this.addAlert(dataAlert);
      }

      deleteProjectById(id) {
        this.services.deleteProject(id).then((data) => {
            this.$state.reload();
        });
      }


      setSingleTickedTrue(elem) {
        elem["ticked"] = true;
      }

      setTickedProperty(array, val) {
          array.forEach((entry) => {
              entry["ticked"] = val;
          });
      }

      openPickerInicio() {
           this.datePickerInicio.status = true;
       }

       openPickerFin() {
            this.datePickerFin.status = true;
        }

      clearValuesUbicacion(type) {
        if (this.currentProject.comunas) {
          this.currentProject.comunas.splice(0, this.currentProject.comunas.length);
        }
        if (type === "Dirección") {
          this.dirNoValidate = 'comunas';
          this.noDir = false;
          this.setTickedProperty(this.comunas, false);
        } else if (type === "Comunas") {
          this.currentProject.direccion = "";
          this.dirNoValidate = 'direccion';
          this.noDir = false;
        } else {
          this.dirNoValidate = "";
          this.noDir = true;
        }
      }

      loadTags($query) {
          return this.poblacionesMeta.filter(function(tag) {
            return tag.nombre.toLowerCase().indexOf($query.toLowerCase()) !== -1;
          });
      };

      loadYears() {
        this.validDate = "";
        let start = this.currentProject.fechaInicio;
        let end = this.currentProject.fechaFin;
        if (start && end) {
          if (end < start) {
            this.validDate = "La fecha de fin debe ser mayor a la fecha de inicio";
            this.currentProject.fechaFin = null;
          } else {
            this.currentProject.presupuestosPorAnio = [];
            this.totalBudget = 0;
            for (var y = start.getFullYear(); y <= end.getFullYear(); y++) {
              var p : Presupuesto = {
                'anio': y,
                'presupuesto': 0,
              };
              this.currentProject.presupuestosPorAnio.push(p);
            }
          }
        }
      }

      getTotalBudget() {
        this.totalBudget = 0;
        this.validDate = "";
        var pj = this.currentProject;
        if (pj && pj.presupuestosPorAnio && pj.presupuestosPorAnio.length > 0) {
          this.currentProject.presupuestosPorAnio.forEach((p) => {
            this.totalBudget += Number(p.presupuesto);
          });
        }
        return this.totalBudget;
      }

      moveProject() {
        var referralDivFactory = this.$compile(" <moveproject currentproject='formCtrl.currentProject' jurisdiccion='formCtrl.jurisdiccion'></moveproject> ");
        var referralDiv = referralDivFactory(this.$scope);
        var containerDiv = document.getElementById('moveprojectid');
        angular.element(containerDiv).append(referralDiv);
      }

      changeOperativeObjective(id) {
        this.currentProject.idObjetivoOperativo2 = id;
      }

      cancel() {
        var formDiv = document.getElementsByTagName('formproject');
        angular.element(formDiv).remove();
      }

      addNotification(data) {
        var referralDivFactory = this.$compile(' <notification type="' +data.type+ '" icon="' +data.icon+ '" title="' +data.title+ '" text="' +data.text+ '" '+data.action+'="' +data.valueAction+ '" textlink="' +data.textlink+ '"></notification> '); // tslint:disable-line
        var referralDiv = referralDivFactory(this.$scope);
        var containerDiv = document.getElementById('notifications');
        angular.element(containerDiv).append(referralDiv);
        this.goToTop();
      }

      goToElement(idElement) {
          (<any>$('html,body')).animate({
            scrollTop: $("#" + idElement).offset().top},
          500);
      }

      goToTop() {
          (<any>$('html,body')).animate({
            scrollTop: 0},
          500);
      }

    }

    export let formProjectComponent = {
        bindings: {
            idobjetivo: '=',
            idproject: '='
        },
        templateUrl: template,
        controller: FormProjectComponentController,
        controllerAs: 'formCtrl',
    };

}

export = Home;
