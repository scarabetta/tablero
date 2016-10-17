import {GeneralServices} from "../services/services.ts";
import {Search} from "../services/search.ts";
import {Jurisdiccion} from "../models/jurisdiccion.ts";
import {Usuario} from "../models/jurisdiccion.ts";

module Home {

    export class HomeController {

        private jurisdiccion:Jurisdiccion;
        private objetivosJurisdiccionales: any;
        private idjurisdiccionKey = 'idJurisdiccionStorage';
        private currentUserKey = 'currentUser';
        private rolUser: string;

        /*@ngInject*/
        constructor(private services:GeneralServices, private $scope:ng.IScope, private $compile: ng.ICompileService, private localStorageService:angular.local.storage.ILocalStorageService,
          $sce: ng.ISCEService, private Upload: angular.angularFileUpload.IUploadService, // tslint:disable-line variable-name
          private search:Search, private $state: ng.ui.IStateService) {

            search.setText("");
            var userData = this.localStorageService.get(this.currentUserKey);
            var user = <Usuario>userData;
            this.rolUser = user.roles[0].nombre;
            (<any>$('#simple-menu')).sidr({
              body: '.styleMenu',
              onOpen: function(){
                  (<any>$(".insideClass")).addClass('col-md-10 col-sm-10');
                  (<any>$("sidemenu")).css( "position", "relative" );
              },
              onClose: function(){
                (<any>$("sidemenu")).css( "position", "absolute" );
                (<any>$(".insideClass")).removeClass('col-md-10 col-sm-10');
              }
            });
            (<any>$("document")).ready(function($){

                (<any>$(window)).scroll(function () {
                    if ((<any>$(this)).scrollTop() > 60) {
                        (<any>$('.navbar-default')).addClass("scroll");
                    } else {
                        (<any>$('.navbar-default')).removeClass("scroll");
                    }
                });
            });
            var idJurisdiccionStorage = this.localStorageService.get(this.idjurisdiccionKey);
            if (idJurisdiccionStorage) {
              services.getJurisdiccion(idJurisdiccionStorage).then((data) => {
                this.jurisdiccion = data;
                this.objetivosJurisdiccionales = data.objetivosJurisdiccionales;
                setTimeout(() => {
                  this.showAll();
                }, 0);

                this.verifyProjects();

                // if (!this.localStorageService.get('flagOnboarding') && this.$state.current.name === 'home.tree') {
                //
                //   var statesdemo = [
                //     {
                //       title: 'Bienvenido a PIG',
                //       html: 'Te contamos los principales beneficios del nuevo sistema que simplifica la gestión de proyectos',
                //       buttons: { Siguiente: 1 },
                //       focus: 0,
                //       submit: function(e, v, m, f) {
                //         (<any>$).prompt.nextState();
                //         return false;
                //       }
                //     },
                //     {
                //       title: 'Importación de proyectos',
                //       html: 'Para facilitar el proceso de carga, permitimos el alta de proyectos por medio de Excel para evitar la carga manual.',
                //       buttons: { Anterior: -1, Siguiente: 1 },
                //       focus: 1,
                //       opacity: 0.1,
                //       position: { container: '#dropdownExcel', x: 100, y: -70, width: 500, arrow: 'lm' },
                //       submit: function(e, v, m, f) {
                //         if (v === -1) {
                //           (<any>$).prompt.prevState();
                //           return false;
                //         } else if (v === 1) {
                //           data.objetivosJurisdiccionales.forEach((entryJuris) => {
                //               (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('show');
                //               entryJuris.objetivosOperativos.forEach((entryOp) => {
                //                   (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('show');
                //               });
                //           });
                //           (<any>$).prompt.nextState();
                //           return false;
                //         }
                //       }
                //     },
                //     {
                //       title: "Visualización de proyectos",
                //       html: 'Los proyectos se estructuran en forma de árbol, agrupando los mismos por objetivo estratégico y operativo.',
                //       buttons: { Anterior: -1, Siguiente: 1 },
                //       focus: 1,
                //       position: { container: '#add-strategic-objetive', x: 0, y: -145, width: 500, arrow: 'bl' },
                //       submit: function(e, v, m, f) {
                //         if (v === -1) {
                //           data.objetivosJurisdiccionales.forEach((entryJuris) => {
                //               (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('hide');
                //               entryJuris.objetivosOperativos.forEach((entryOp) => {
                //                   (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('hide');
                //               });
                //           });
                //           (<any>$).prompt.prevState();
                //           return false;
                //         } else if (v === 1) {
                //           (<any>$('#addProjectButton-' + data.objetivosJurisdiccionales[0].objetivosOperativos[0].idObjetivoOperativo)).trigger( "click" );
                //           (<any>$).prompt.nextState();
                //           return false;
                //         }
                //       }
                //     },
                //     {
                //       title: 'Menos campos',
                //       html: 'Simplificamos los campos requeridos al momento del alta inicial de proyecto.',
                //       buttons: { Anterior: -1, Siguiente: 1 },
                //       focus: 1,
                //       position: { container: '#projectFormID', x:20, y: -150, width: 500, arrow: 'bl' },
                //       submit: function(e, v, m, f) {
                //         if (v === -1) {
                //           var formDiv = document.getElementsByTagName('formproject');
                //           angular.element(formDiv).remove();
                //           (<any>$).prompt.prevState();
                //           return false;
                //         } else if (v === 1) {
                //           (<any>$).prompt.nextState();
                //           return false;
                //         }
                //       }
                //     },
                //     {
                //       title: 'Guardar borrador',
                //       html: 'En caso de no contar con toda la información, se permite guardar el proyecto como borrador y completarlo más adelante.',
                //       buttons: { Anterior: -1, Finalizar: 1 },
                //       focus: 1,
                //       position: { container: '#draftButton', x: 180, y: -65, width: 400, arrow: 'lm' },
                //       submit: function(e, v, m, f) {
                //         if (v === -1) {
                //           (<any>$).prompt.prevState();
                //           return false;
                //         } else if (v === 1) {
                //           (<any>$('html,body')).animate({
                //             scrollTop: 0},
                //           500);
                //           data.objetivosJurisdiccionales.forEach((entryJuris) => {
                //               (<any>$("#grupo-level-" + entryJuris.idObjetivoJurisdiccional)).collapse('hide');
                //               entryJuris.objetivosOperativos.forEach((entryOp) => {
                //                   (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('hide');
                //               });
                //           });
                //           var formDiv = document.getElementsByTagName('formproject');
                //           angular.element(formDiv).remove();
                //           (<any>$).prompt.close();
                //         }
                //       }
                //     }
                //   ];
                //
                //   (<any>$).prompt(statesdemo);
                //   this.localStorageService.set('flagOnboarding', true);
                // }

                //Note: this code should be moved on controller's refactoring phase
                var completeness = this.getJurisdiccionCompleteness(this.objetivosJurisdiccionales);
                if (completeness.complete) {
                  this.showCompleteStatus();
                }
                if (completeness.incomplete) {
                  this.showIncompleteStatus();
                }
              });
            } else {
                this.services.jurisdicciones().then((data) => {
                  if (data) {
                    this.localStorageService.set(this.idjurisdiccionKey, data[0].idJurisdiccion);
                    this.$state.reload();
                  }
                });
            }

        }

        addStrategicObjective(idJurisdiccion) {
          if (!angular.element(document.getElementsByTagName('formstrategicobjective')).length) {
            var referralDivFactory = this.$compile(" <formstrategicobjective idjurisdiccion='" + idJurisdiccion + "'></formstrategicobjective> ");
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById('add-strategic-objetive');
            angular.element(containerDiv).append(referralDiv);
          }
        }

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

        viewProject(idProject) {
          if (angular.element(document.getElementsByTagName('viewproject')).length) {
              var projectTag = document.getElementsByTagName('viewproject');
              angular.element(projectTag).remove();
          }
          var referralDivFactory = this.$compile( " <viewproject idproject='" + idProject + "'></viewproject> " );
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById("proyecto-" + idProject);
          angular.element(containerDiv).append(referralDiv);
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
                  if (entryOp.proyectos.length > 0) {
                      (<any>$("#grupo-level-2-" + entryOp.idObjetivoOperativo)).collapse('show');
                  }
              });
          });
        }

        labels(idProyecto) {
            if (!angular.element(document.getElementsByTagName('formlabels')).length) {
              var referralDivFactory = this.$compile(" <formlabels idproyecto='" + idProyecto + "'></formlabels> ");
              var referralDiv = referralDivFactory(this.$scope);
              var containerDiv = document.getElementById("proyecto-" + idProyecto);
              angular.element(containerDiv).append(referralDiv);
              setTimeout(() => {
                this.goToLabels();
              }, 0);
            }
        }

        addNotification(data) {
          var formDiv = document.getElementsByTagName('notification');
          angular.element(formDiv).remove();
          var referralDivFactory = this.$compile(' <notification type="' + data.type + '" icon="' + data.icon + '" title="' + data.title + '" text="' + data.text + '" ' + data.action + '="'  + data.valueAction + '" textlink="' + data.textlink + '" callback="' + 'homeCtrl.' + data.callback + '"></notification> '); // tslint:disable-line max-line-length
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById('notifications');
          angular.element(containerDiv).append(referralDiv);
          this.goToTop();
        }

        showCompleteStatus() {
          var notificationDataCompleto = {
            "type" : "success",
            "icon" : "ok-sign",
            "title" : "Completo",
            "text" : "Los proyectos en verde se agregaron en modo borrador. Podrás seguir modificándolos hasta que la SECPECG los verifique. Es necesario que los presentes para que la secretaría pueda verlos.", // tslint:disable-line max-line-length
            "action": "hascallback",
            "valueAction" : true,
            "textlink": "Presentar todos los proyectos completos",
            "callback": 'presentAllProjects()'
          };

          this.addNotification(notificationDataCompleto);
        }

        showIncompleteStatus() {
          var notificationDataIncompleto = {
            "type" : "warning",
            "icon" : "exclamation-sign",
            "title" : "Incompleto",
            "text" : "Los proyectos señalados en rojo tienen datos incompletos." // tslint:disable-line
          };

          this.addNotification(notificationDataIncompleto);
        }

        getJurisdiccionCompleteness(objetivosJurisdiccionales) {
          var objJurisdiccionales = objetivosJurisdiccionales;
          var objOperativos;
          var proyectos;
          var jurisI = 0;
          var operI = 0;
          var proyI = 0;
          var completeness = {
            complete: false,
            incomplete: false
          };
          var completeStatesSet = function(completeness){
            return completeness.complete && completeness.incomplete;
          };

          while ( (jurisI < objJurisdiccionales.length) &&
                  (!completeStatesSet(completeness)) ) {
            objOperativos = objJurisdiccionales[jurisI].objetivosOperativos;

            operI = 0;
            while ( (operI < objOperativos.length) &&
                  (!completeStatesSet(completeness)) ) {
              proyectos = objOperativos[operI].proyectos;

              proyI = 0;
              while ( (proyI < proyectos.length) &&
                (!completeStatesSet(completeness)) ) {

                if (proyectos[proyI].estado === 'Completo') {
                  completeness.complete = true;
                }
                if (proyectos[proyI].estado === 'Incompleto') {
                  completeness.incomplete = true;
                }

                proyI++;
              }

              operI++;
            }

            jurisI++;
          }

          return completeness;
        };

        verifyProjects() {
          var projects = 0;
          this.jurisdiccion.objetivosJurisdiccionales.forEach((oj) => {
            oj.objetivosOperativos.forEach((oo) => {
              projects = projects + oo.proyectos.length;
            });
          });
          if (projects === 0 && this.$state.current.name === 'home.tree'
            && ((!(<any>this.$state.params).fromState) || (<any>this.$state.params).fromState !== 'home')) {
            console.log(projects);
            this.$state.go('home');
          }
        }

        goToLabels() {
          (<any>$('html,body')).animate({scrollTop: $('#labelsTop').offset().top - 60}, 1000);
        }

        goToTop() {
            (<any>$('html,body')).animate({
              scrollTop: 0},
            500);
        }

        presentAllProjects() {
          this.services.presentAllProject(this.localStorageService.get(this.idjurisdiccionKey)).then((data) => {
            this.$state.reload();
          });
        }

    }
}

export = Home;
