import {GeneralServices} from "../services/services.ts";
import {Search} from "../services/search.ts";
import {Jurisdiccion} from "../models/jurisdiccion.ts";
const config = require('webpack-config-loader!app-config');

module Home {

    export class HomeController {

        private jurisdiccion:Jurisdiccion[];
        private objetivosJurisdiccionales: any;
        private importURL:string;
        private pisarProyectos:boolean;
        private idjurisdiccionKey = 'idJurisdiccionStorage';

        /*@ngInject*/
        constructor(private services:GeneralServices, private $scope:ng.IScope, private $compile: ng.ICompileService, private localStorageService:angular.local.storage.ILocalStorageService,
          $sce: ng.ISCEService, private Upload: angular.angularFileUpload.IUploadService, private $http: ng.IHttpService, private search:Search, // tslint:disable-line variable-name
          private $state: ng.ui.IStateService) {
            search.setText("");
            var idJurisdiccionStorage = this.localStorageService.get(this.idjurisdiccionKey);
            if (idJurisdiccionStorage) {
              services.getJurisdiccion(idJurisdiccionStorage).then((data) => {
                this.jurisdiccion = data;
                this.objetivosJurisdiccionales = data.objetivosJurisdiccionales;

                if (!this.localStorageService.get('flagOnboarding') && this.$state.current.name === 'home.tree') {

                  var statesdemo = [
                    {
                      title: 'Bienvenido a PGI',
                      html: 'Te contamos los principales beneficios del nuevo sistema que simplifica la gestión de proyectos',
                      buttons: { Siguiente: 1 },
                      focus: 0,
                      submit: function(e, v, m, f) {
                        (<any>$).prompt.nextState();
                        return false;
                      }
                    },
                    {
                      title: 'Importación de proyectos',
                      html: 'Para facilitar el proceso de carga, permitimos el alta de proyectos por medio de Excel para evitar la carga manual.',
                      buttons: { Anterior: -1, Siguiente: 1 },
                      focus: 1,
                      opacity: 0.1,
                      position: { container: '#dropdownExcel', x: 100, y: -70, width: 500, arrow: 'lm' },
                      submit: function(e, v, m, f) {
                        if (v === -1) {
                          (<any>$).prompt.prevState();
                          return false;
                        } else if (v === 1) {
                          data.objetivosJurisdiccionales.forEach((entryJuris) => {
                              (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('show');
                              entryJuris.objetivosOperativos.forEach((entryOp) => {
                                  (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('show');
                              });
                          });
                          (<any>$).prompt.nextState();
                          return false;
                        }
                      }
                    },
                    {
                      title: "Visualización de proyectos",
                      html: 'Los proyectos se estructuran en forma de árbol, agrupando los mismos por objetivo estratégico y operativo.',
                      buttons: { Anterior: -1, Siguiente: 1 },
                      focus: 1,
                      position: { container: '#add-strategic-objetive', x: 0, y: -145, width: 500, arrow: 'bl' },
                      submit: function(e, v, m, f) {
                        if (v === -1) {
                          data.objetivosJurisdiccionales.forEach((entryJuris) => {
                              (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('hide');
                              entryJuris.objetivosOperativos.forEach((entryOp) => {
                                  (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('hide');
                              });
                          });
                          (<any>$).prompt.prevState();
                          return false;
                        } else if (v === 1) {
                          (<any>$('#addProjectButton-' + data.objetivosJurisdiccionales[0].objetivosOperativos[0].idObjetivoOperativo)).trigger( "click" );
                          (<any>$).prompt.nextState();
                          return false;
                        }
                      }
                    },
                    {
                      title: 'Menos campos',
                      html: 'Simplificamos los campos requeridos al momento del alta inicial de proyecto.',
                      buttons: { Anterior: -1, Siguiente: 1 },
                      focus: 1,
                      position: { container: '#projectFormID', x:20, y: -150, width: 500, arrow: 'bl' },
                      submit: function(e, v, m, f) {
                        if (v === -1) {
                          var formDiv = document.getElementsByTagName('formproject');
                          angular.element(formDiv).remove();
                          (<any>$).prompt.prevState();
                          return false;
                        } else if (v === 1) {
                          (<any>$).prompt.nextState();
                          return false;
                        }
                      }
                    },
                    {
                      title: 'Guardar borrador',
                      html: 'En caso de no contar con toda la información, se permite guardar el proyecto como borrador y completarlo más adelante.',
                      buttons: { Anterior: -1, Finalizar: 1 },
                      focus: 1,
                      position: { container: '#draftButton', x: 180, y: -65, width: 400, arrow: 'lm' },
                      submit: function(e, v, m, f) {
                        if (v === -1) {
                          (<any>$).prompt.prevState();
                          return false;
                        } else if (v === 1) {
                          (<any>$('html,body')).animate({
                            scrollTop: 0},
                          500);
                          data.objetivosJurisdiccionales.forEach((entryJuris) => {
                              (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('hide');
                              entryJuris.objetivosOperativos.forEach((entryOp) => {
                                  (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('hide');
                              });
                          });
                          var formDiv = document.getElementsByTagName('formproject');
                          angular.element(formDiv).remove();
                          (<any>$).prompt.close();
                        }
                      }
                    }
                  ];

                  (<any>$).prompt(statesdemo);
                  this.localStorageService.set('flagOnboarding', true);
                }
              });
            }
            this.importURL = config.authBaseUrl + 'api/importar/proyecto';
            this.pisarProyectos = false;

        }

        addStrategicObjective(idJurisdiccion) {
          if (!angular.element(document.getElementsByTagName('formstrategicobjective')).length) {
            var referralDivFactory = this.$compile(" <formstrategicobjective idjurisdiccion='" + idJurisdiccion + "'></formstrategicobjective> ");
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById('add-strategic-objetive');
            angular.element(containerDiv).append(referralDiv);
          }
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

        editStrategicObjective(idStrategicObjective) {
          if (!angular.element(document.getElementsByTagName('formstrategicobjective')).length) {
            var referralDivFactory = this.$compile( " <formstrategicobjective idobjetivoestrategico='" + idStrategicObjective + "'></formstrategicobjective> " );
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById("es-" + idStrategicObjective);
            angular.element(containerDiv).append(referralDiv);
          } else {
            var notificationData = {
              "type" : "warning",
              "icon" : "exclamation-sign",
              "title" : "Alerta",
              "text" : "No es posible modificar un objetivo mientras haya otra modificación o alta en curso.",
              "action": "gotoestrategico",
              "valueAction" : true,
              "textlink": "Ir al formulario"
            };
            this.addNotification(notificationData);
          }
        }

        addOperativeObjective(idObjetivoEstrategico, elem) {
          (<any>$("#grupo-level-" + elem)).collapse('show');

          if (!angular.element(document.getElementsByTagName('formoperativeobjective')).length) {
            var referralDivFactory = this.$compile(" <formoperativeobjective idobjetivoestrategico='" + idObjetivoEstrategico + "'></formoperativeobjective> ");
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById('add-operative-objetive-' + idObjetivoEstrategico);
            angular.element(containerDiv).append(referralDiv);
          } else {
            var notificationData = {
              "type" : "warning",
              "icon" : "exclamation-sign",
              "title" : "Alerta",
              "text" : "No es posible agregar un objetivo mientras haya otra modificación o alta en curso.",
              "action": "gotooperativo",
              "valueAction" : true,
              "textlink": "Ir al formulario"
            };
            this.addNotification(notificationData);
          }
        }

        editOperativeObjective(idOperativeObjective) {
          if (!angular.element(document.getElementsByTagName('formoperativeobjective')).length) {
            var referralDivFactory = this.$compile( " <formoperativeobjective idoperativeobjective='" + idOperativeObjective + "'></formoperativeobjective> " );
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById("op-" + idOperativeObjective);
            angular.element(containerDiv).append(referralDiv);
          } else {
            var notificationData = {
              "type" : "warning",
              "icon" : "exclamation-sign",
              "title" : "Alerta",
              "text" : "No es posible modificar un objetivo mientras haya otra modificación o alta en curso.",
              "action": "gotooperativo",
              "valueAction" : true,
              "textlink": "Ir al formulario"
            };
            this.addNotification(notificationData);
          }
        }

        editProject(idProject) {
          if (!angular.element(document.getElementsByTagName('formproject')).length) {
            var referralDivFactory = this.$compile( " <formproject idproject='" + idProject + "'></formproject> " );
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById("proyecto-" + idProject);
            angular.element(containerDiv).append(referralDiv);
          } else {
            var notificationData = {
              "type" : "warning",
              "icon" : "exclamation-sign",
              "title" : "Alerta",
              "text" : "No es posible modificar un proyecto mientras haya otra modificación o alta en curso.",
              "action": "gotoform",
              "valueAction" : true,
              "textlink": "Ir al formulario"
            };
            this.addNotification(notificationData);
          }
        }

        addProject(idObjetivo) {
          if (!angular.element(document.getElementsByTagName('formproject')).length) {
              var referralDivFactory = this.$compile(" <formproject idobjetivo='" + idObjetivo + "'></formproject> ");
              var referralDiv = referralDivFactory(this.$scope);
              var containerDiv = document.getElementById("op-" + idObjetivo);
              angular.element(containerDiv).append(referralDiv);
          } else {
            var notificationData = {
              "type" : "warning",
              "icon" : "exclamation-sign",
              "title" : "Alerta",
              "text" : "No es posible agregar un proyecto mientras haya otra modificación o alta en curso.",
              "action": "gotoform",
              "valueAction" : true,
              "textlink": "Ir al formulario"
            };
            this.addNotification(notificationData);
          }
        }

        collapseAll() {
          this.objetivosJurisdiccionales.forEach((entryJuris) => {
              (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('hide');
              entryJuris.objetivosOperativos.forEach((entryOp) => {
                  (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('hide');
              });
          });
        }

        showAll() {
          this.objetivosJurisdiccionales.forEach((entryJuris) => {
              (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('show');
              entryJuris.objetivosOperativos.forEach((entryOp) => {
                  (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('show');
              });
          });
        }

        addNotification(data) {
          var formDiv = document.getElementsByTagName('alertmodal');
          angular.element(formDiv).remove();
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

        submit() {
          var f = document.getElementById('archivoAImportar');
          var file = (<any>f).files[0];
          var fd = new FormData();
          fd.append('archivoAImportar', file);
          fd.append('pisarProyectos', this.pisarProyectos);
          this.$http.post(this.importURL, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined},
            responseType:'arraybuffer'
          })
          .then((response) => {
            console.log(response);
            if (response.status === 200) {
              var scope = this;
                this.$state.go('home.tree').then(function() {
                  var notificationDataCompleto = {
                    "type" : "success",
                    "icon" : "ok-sign",
                    "title" : "Completo",
                    "text" : "Los proyectos en verde se agregaron en modo borrador. Podrás seguir modificándolos hasta que la SECPECG los verifique. Es necesario que los presentes para que la secretaría pueda verlos." // tslint:disable-line
                  };
                  var notificationDataIncompleto = {
                    "type" : "warning",
                    "icon" : "exclamation-sign",
                    "title" : "Incompleto",
                    "text" : "Los proyectos señalados en rojo tienen datos incompletos." // tslint:disable-line
                  };
                  scope.addNotification(notificationDataCompleto);
                  scope.addNotification(notificationDataIncompleto);
                });
            } else if (response.status === 202) {
              var addZero = function(i) {
                if (i < 10) {
                    i = "0" + i;
                }
                return i;
              };
              var date = new Date();
              var year = addZero(date.getFullYear());
              var month = addZero(date.getMonth());
              var day = addZero(date.getDate());
              var hour = addZero(date.getHours());
              var minutes = addZero(date.getMinutes());
              var fileName = 'PGI_ProyectosNoImportados_' + year + month + day
                              + '_' + hour + minutes + '.xlsx';
              this.saveData(response.data, fileName);
            }
          })
          .catch((response) => console.log(response.data));
        }

    }
}

export = Home;
