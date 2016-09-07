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
      private title: string;
      private currentProject:Proyecto;
      private poblacionesMeta:PoblacionMeta[];
      private poblacionesMetaPlaceholder = 'Comunas';
      private ejesDeGobierno:EjeDeGobierno[];
      private totalBudgetOtherSources: number;
      private totalBudget: number;
      private comunas:Comuna[];
      private comunasPlaceholder = 'Comunas';
      private idobjetivo: number;
      private idproject: number;
      private allInputs: boolean;
      private jurisdiccion: Jurisdiccion;
      private idjurisdiccionKey = 'idJurisdiccionStorage';
      private countForm: string;
      private percentForm: number;
      private validDate: string;
      private areaNombre:string;
      private area: any;
      private moveOptions: any;
      private actionMove:string;
      private changingStateFlag:boolean;
      private validators = new Array<any>();
      private datePickerInicio = {
        status: false
      };
      private datePickerFin = {
        status: false
      };

      /*@ngInject*/
      constructor(private services:GeneralServices, private $http: ng.IHttpService, private $state:ng.ui.IStateService, private $scope:ng.IScope,
        private localStorageService:angular.local.storage.ILocalStorageService, private $compile: ng.ICompileService) {
          // (<any>$(".pull-right")).pin({containerSelector: ".contentFormProyect"});
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
            this.title = 'Modificar Proyecto';
            services.getProject(this.idproject).then((data) => {
              data.fechaInicio = new Date(data.fechaInicio);
              data.fechaFin = new Date(data.fechaFin);
              this.currentProject = data;
              this.actionMove = data.estado;
              this.initValidators();
              if (data.area) {
                this.areaNombre = data.area.nombre;
              }
              services.comunas().then((data) => {
                this.comunas = data;
                this.getTotalBudget();
                this.getTotalBudgetOtherSources();
              });
              this.changingStateFlag = false;
              services.getMoveOptions(this.idproject).then((data) => {
                console.log(data);
                this.moveOptions = data;
              });
            });
          } else {
            this.title = 'Nuevo Proyecto';
            this.initValidators();
              services.comunas().then((data) => {
                this.comunas = data;
              });
              this.currentProject.idObjetivoOperativo2 = this.idobjetivo;
              this.allInputs = false;
              this.changingStateFlag = false;
          }


          $scope.$watch('formCtrl.currentProject', (newVal, oldVal) => {
            var emptyCount = 0;
            this.validators.forEach((validator) => {
              if (!validator()) {
                emptyCount++;
              }
            });
            if (emptyCount === 0) {
                this.allInputs = true;
                this.countForm = "Has ingresado todos los campos.";
                this.percentForm = Math.round(((this.validators.length - 2) - emptyCount) * (100 / (this.validators.length - 2)));
            } else {
                this.allInputs = false;
                this.percentForm = Math.round(((this.validators.length - 2) - emptyCount) * (100 / (this.validators.length - 2)));
                this.countForm = "Te quedan " + emptyCount + " datos por ingresar";
            }
          }, true);

          $scope.$watch(() => {
            return (this.currentProject.comunas && this.currentProject.comunas.length) ? this.currentProject.comunas.length : 0;
          }, (value) => {
            this.comunasPlaceholder = value > 0 ? '' : 'Comunas';
          });

          $scope.$watch(() => {
            return (this.currentProject.poblacionesMeta && this.currentProject.poblacionesMeta.length) ? this.currentProject.poblacionesMeta.length : 0;
          }, (value) => {
            this.poblacionesMetaPlaceholder = value > 0 ? '' : 'Ej. Jubilados, Estudiantes';
          });

      };

      cleanCheckEjes() {
        console.log(this.currentProject.ejesDeGobierno);
        if ((<any>$("#no-selection")).prop("checked")) {
          this.currentProject.ejesDeGobierno = [];
        } else {
          (<any>$(this)).prop("checked", !(<any>$(this)).prop("checked"));
        }
      }

      setState(state) {
        this.changingStateFlag = true;
        this.actionMove = state;
      }

      checkNoSelection() {
        console.log(this.currentProject.ejesDeGobierno);
        if (this.currentProject.ejesDeGobierno.length > 0) {
          (<any>$("#no-selection")).prop("checked", false);
        } else {
          (<any>$("#no-selection")).prop("checked", true);
        }
      }

      isEmpty(array) {
        return array.length === 0;
      }

      isFalsy(field) {
        return field === undefined || field === null || this.isEmpty(field);
      }

      createValidatorRequired(obj, property) {
        var scope = this;
        return function(){
          return !scope.isFalsy(obj[property]);
        };
      }

      createValidatorComposeRequired(obj, property, requiredProperty, requiredValue) {
        var scope = this;
        return function(){
          return (obj[requiredProperty] === requiredValue) === !scope.isFalsy(obj[property]);
        };
      }

      onChangeArea() {
        var scope = this;
        this.jurisdiccion.areas.forEach(function(entry) {
          if (entry.nombre === this.areaNombre) {
            scope.area = entry;
          }
        }, this);
        this.currentProject.area = this.area;
      }

      initValidators() {
          this.validators.push(this.createValidatorRequired(this.currentProject, 'nombre'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'descripcion'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'meta'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'unidadMeta'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'poblacionAfectada'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'liderProyecto'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'area'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'cambioLegislativo'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'fechaInicio'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'fechaFin'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'prioridadJurisdiccional'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'presupuestosPorAnio'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'ejesDeGobierno'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'poblacionesMeta'));
          this.validators.push(this.createValidatorRequired(this.currentProject, 'tipoUbicacionGeografica'));
          this.validators.push(this.createValidatorComposeRequired(this.currentProject, 'direccion', 'tipoUbicacionGeografica', 'Dirección'));
          this.validators.push(this.createValidatorComposeRequired(this.currentProject, 'comunas', 'tipoUbicacionGeografica', 'Comunas'));
      }

      presentProject() {
        var scope = this;
        if (this.changingStateFlag) {
          this.services.changeState(this.actionMove, this.currentProject).then((data) => {
            console.log(data);
            scope.$state.reload().then(function() {
                var notificationData = {
                  "type" : "success",
                  "icon" : "ok-sign",
                  "title" : "Ok",
                  "text" : "El proyecto se guardó con éxito." // tslint:disable-line
                };
                scope.addNotification(notificationData);
            });
          });
        } else {
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

      }

      saveProject() {
        var scope = this;
        console.log(this.currentProject);
        if (this.idproject) {
          this.services.changeState(this.actionMove, this.currentProject).then((data) => {
            console.log(data);
            this.services.updateProject(this.currentProject).then((data) => {
                this.$state.reload();
             });
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
      }

      loadTags($query) {
          return this.poblacionesMeta.filter(function(tag) {
            return tag.nombre.toLowerCase().indexOf($query.toLowerCase()) !== -1;
          });
      };

      loadComunas($query) {
        return this.comunas.filter(function(tag) {
          return tag.nombre.toLowerCase().indexOf($query.toLowerCase()) !== -1;
        });
      }

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
            this.totalBudgetOtherSources = 0;
            for (var y = start.getFullYear(); y <= end.getFullYear(); y++) {
              var p : Presupuesto = {
                'anio': y,
                'presupuesto': 0,
                'otrasFuentes': 0
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

      getTotalBudgetOtherSources() {
        this.totalBudgetOtherSources = 0;
        this.validDate = "";
        var pj = this.currentProject;
        if (pj && pj.presupuestosPorAnio && pj.presupuestosPorAnio.length > 0) {
          this.currentProject.presupuestosPorAnio.forEach((p) => {
            this.totalBudgetOtherSources += Number(p.otrasFuentes);
          });
        }
        return this.totalBudgetOtherSources;
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
