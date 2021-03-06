import {GeneralServices} from "../services/services";
import {Search} from "../services/search";
import {Jurisdiccion} from "../models/jurisdiccion";
import {Usuario} from "../models/jurisdiccion";

module Home {

    export class HomeController {

        private jurisdiccion:Jurisdiccion;
        private objetivosJurisdiccionales: any;
        private idjurisdiccionKey = 'idJurisdiccionStorage';
        private currentUserKey = 'currentUser';
        private rolUser: string;
        private userOperador: boolean;
        private userSecretaria: boolean;

        /*@ngInject*/
        constructor(private services:GeneralServices, private $scope:ng.IScope, private $compile: ng.ICompileService, private localStorageService:angular.local.storage.ILocalStorageService,
          $sce: ng.ISCEService, private Upload: angular.angularFileUpload.IUploadService, // tslint:disable-line variable-name
          private search:Search, private $state: ng.ui.IStateService, $rootScope: ng.IRootScopeService) {

            search.setText("");
            var userData = this.localStorageService.get(this.currentUserKey);
            var user = <Usuario>userData;
            this.rolUser = user.roles[0].nombre;
            if (user) {
              user.roles.forEach((rol) => {
                  if (rol.nombre === "Operador de jurisdicción") {
                    this.userOperador = true;
                  }
                  if (rol.nombre === "Secretaría") {
                    this.userSecretaria = true;
                  }
              });
            }
            (<any>$('#simple-menu')).sidr({
              body: '.styleMenu',
              onOpen: function(){
                  (<any>$(".insideClass")).addClass('col-md-10 col-sm-9');
                  (<any>$("sidemenu")).css( "position", "relative" );
              },
              onClose: function(){
                (<any>$("sidemenu")).css( "position", "absolute" );
                (<any>$(".insideClass")).removeClass('col-md-10 col-sm-9');
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
                $rootScope.$broadcast('jurisdiccion:updated', data);
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

                let projectStatus = this.inspectProjectStatus(this.objetivosJurisdiccionales);
                if (projectStatus.completed > 0) {
                  this.showCompleteStatus();
                }
                if (projectStatus.incompleted > 0) {
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

            // Si hay notificationes por mostrar, las mostramos
            let notifications = this.services.loadDeferredNotifications();
            notifications.forEach(n => this.appendNotification(n));
        }

        addStrategicObjective(idJurisdiccion) {
          let strategic = $('formstrategicobjective').length > 0;
          let operative = $('formoperativeobjective').length > 0;
          let project = $('formproject').length > 0;
          if (strategic || operative || project) {
            return this.addNotification({
              type: 'warning',
              icon: 'exclamation-sign',
              title: 'Alerta',
              text: 'No es posible agregar un objetivo mientras haya otra modificación o alta en curso.',
              action: strategic ? 'gotoestrategico' : (operative ? 'gotooperativo' : 'gotoform'),
              valueAction: true,
              textlink: 'Ir al formulario'
            });
          }
          this.removerForms(['viewproject']);
          let referralDivFactory = this.$compile(" <formstrategicobjective idjurisdiccion='" + idJurisdiccion + "'></formstrategicobjective> ");
          let referralDiv = referralDivFactory(this.$scope);
          let containerDiv = $('#add-strategic-objetive');
          angular.element(containerDiv).append(referralDiv);
          this.goToElement('formstrategicobjective');
        }

        editStrategicObjective(idStrategicObjective) {
          let strategic = $('formstrategicobjective').length > 0;
          let operative = $('formoperativeobjective').length > 0;
          let project = $('formproject').length > 0;
          if (strategic || operative || project) {
            return this.addNotification({
              type: 'warning',
              icon: 'exclamation-sign',
              title: 'Alerta',
              text: 'No es posible modificar un objetivo mientras haya otra modificación o alta en curso.',
              action: strategic ? 'gotoestrategico' : (operative ? 'gotooperativo' : 'gotoform'),
              valueAction: true,
              textlink: 'Ir al formulario'
            });
          }
          this.removerForms(['viewproject']);
          let referralDivFactory = this.$compile( " <formstrategicobjective idobjetivoestrategico='" + idStrategicObjective + "'></formstrategicobjective> " );
          let referralDiv = referralDivFactory(this.$scope);
          let containerDiv = $("#es-" + idStrategicObjective);
          angular.element(containerDiv).append(referralDiv);
          this.goToElement('formstrategicobjective');
        }

        addOperativeObjective(idObjetivoEstrategico, elem) {
          let strategic = $('formstrategicobjective').length > 0;
          let operative = $('formoperativeobjective').length > 0;
          let project = $('formproject').length > 0;
          if (strategic || operative || project) {
            return this.addNotification({
              type: 'warning',
              icon: 'exclamation-sign',
              title: 'Alerta',
              text: 'No es posible agregar un objetivo mientras haya otra modificación o alta en curso.',
              action: strategic ? 'gotoestrategico' : (operative ? 'gotooperativo' : 'gotoform'),
              valueAction: true,
              textlink: 'Ir al formulario'
            });
          }
          this.removerForms(['viewproject']);
          let referralDivFactory = this.$compile(" <formoperativeobjective idobjetivoestrategico='" + idObjetivoEstrategico + "'></formoperativeobjective> ");
          let referralDiv = referralDivFactory(this.$scope);
          let containerDiv = $('#add-operative-objetive-' + idObjetivoEstrategico);
          angular.element(containerDiv).append(referralDiv);
          this.goToElement('formoperativeobjective');
        }

        editOperativeObjective(idOperativeObjective) {
          let strategic = $('formstrategicobjective').length > 0;
          let operative = $('formoperativeobjective').length > 0;
          let project = $('formproject').length > 0;
          if (strategic || operative || project) {
            return this.addNotification({
              type: 'warning',
              icon: 'exclamation-sign',
              title: 'Alerta',
              text: 'No es posible modificar un objetivo mientras haya otra modificación o alta en curso.',
              action: strategic ? 'gotoestrategico' : (operative ? 'gotooperativo' : 'gotoform'),
              valueAction: true,
              textlink: 'Ir al formulario'
            });
          }
          this.removerForms(['viewproject']);
          let referralDivFactory = this.$compile( " <formoperativeobjective idoperativeobjective='" + idOperativeObjective + "'></formoperativeobjective> " );
          let referralDiv = referralDivFactory(this.$scope);
          let containerDiv = $("#op-" + idOperativeObjective);
          angular.element(containerDiv).append(referralDiv);
          this.goToElement('formoperativeobjective');
        }

        viewProject(proyecto) {
          this.removerForms(['formproject', 'formlabels']);
          var referralDivFactory = this.$compile( " <viewproject idproject='" + proyecto.idProyecto + "'></viewproject> " );
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById("proyecto-" + proyecto.idProyecto);
          angular.element(containerDiv).append(referralDiv);
          this.goToElement('viewproject');
        }

        editProject(proyecto) {
          let strategic = $('formstrategicobjective').length > 0;
          let operative = $('formoperativeobjective').length > 0;
          let project = $('formproject').length > 0;
          if (strategic || operative || project) {
            return this.addNotification({
              type: 'warning',
              icon: 'exclamation-sign',
              title: 'Alerta',
              text: 'No es posible modificar un proyecto mientras haya otra modificación o alta en curso.',
              action: strategic ? 'gotoestrategico' : (operative ? 'gotooperativo' : 'gotoform'),
              valueAction: true,
              textlink: 'Ir al formulario'
            });
          }
          this.removerForms(['viewproject', 'formlabels']);
          let referralDivFactory = this.$compile( " <formproject idproject='" + proyecto.idProyecto + "' estadoproject='" + proyecto.estado + "'></formproject> " );
          let referralDiv = referralDivFactory(this.$scope);
          let containerDiv = $("#proyecto-" + proyecto.idProyecto);
          angular.element(containerDiv).append(referralDiv);
          this.goToElement('formproject');
        }

        addProject(idObjetivo) {
          let strategic = $('formstrategicobjective').length > 0;
          let operative = $('formoperativeobjective').length > 0;
          let project = $('formproject').length > 0;
          if (strategic || operative || project) {
            return this.addNotification({
              type: 'warning',
              icon: 'exclamation-sign',
              title: 'Alerta',
              text: 'No es posible agregar un proyecto mientras haya otra modificación o alta en curso.',
              action: strategic ? 'gotoestrategico' : (operative ? 'gotooperativo' : 'gotoform'),
              valueAction: true,
              textlink: 'Ir al formulario'
            });
          }
          this.removerForms(['viewproject', 'formlabels']);
          let referralDivFactory = this.$compile(" <formproject idobjetivo='" + idObjetivo + "'></formproject> ");
          let referralDiv = referralDivFactory(this.$scope);
          let containerDiv = $("#op-" + idObjetivo);
          angular.element(containerDiv).append(referralDiv);
          this.goToElement('formproject');
        }

        labels(idProyecto) {
            this.removerForms(['viewproject', 'formproject']);
            if (!angular.element(document.getElementsByTagName('formlabels')).length) {
              var referralDivFactory = this.$compile(" <formlabels idproyecto='" + idProyecto + "'></formlabels> ");
              var referralDiv = referralDivFactory(this.$scope);
              var containerDiv = document.getElementById("proyecto-" + idProyecto);
              angular.element(containerDiv).append(referralDiv);
              this.goToElement('formlabels');
            }
        }

        removerForms(forms) {
          forms.forEach((element) => {
            if (angular.element(document.getElementsByTagName(element)).length) {
                var form = document.getElementsByTagName(element);
                angular.element(form).remove();
            }
          });
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

        // Muestra una alerta / notificación. Reemplaza la actual.
        addNotification(data) {
          var formDiv = document.getElementsByTagName('notification');
          angular.element(formDiv).remove();
          this.appendNotification(data);
        }

        // Agrega una alerta / notificacion al contenedor de notificaciones
        private appendNotification(data): void {
            var referralDivFactory = this.$compile(' <notification type="' + data.type + '" icon="' + data.icon + '" title="' + data.title + '" text="' + data.text + '" ' + data.action + '="'  + data.valueAction + '" textlink="' + data.textlink + '" callback="' + 'homeCtrl.' + data.callback + '"></notification> '); // tslint:disable-line max-line-length
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById('notifications');
            angular.element(containerDiv).append(referralDiv);
            this.goToTop();
        }

        showCompleteStatus() {
          this.appendNotification({
            "type" : "success",
            "icon" : "ok-sign",
            "title" : "Completo",
            "text" : "Los proyectos en verde se agregaron en modo borrador. Podrás seguir modificándolos hasta que la SECPECG los verifique. Es necesario que los presentes para que la secretaría pueda verlos.", // tslint:disable-line max-line-length
            "action": "hascallback",
            "valueAction" : true,
            "textlink": "Presentar todos los proyectos completos",
            "callback": 'presentAllProjects()'
          });
        }

        showIncompleteStatus() {
          this.appendNotification({
            "type" : "warning",
            "icon" : "exclamation-sign",
            "title" : "Incompleto",
            "text" : "Los proyectos señalados en rojo tienen datos incompletos." // tslint:disable-line
          });
        }

        private inspectProjectStatus(objetivosJurisdiccionales: Array<any>): any {
            let status = { completed: 0, incompleted: 0 };
            objetivosJurisdiccionales.forEach(j => {
              j.objetivosOperativos.forEach(o => {
                o.proyectos.forEach(p => {
                  if (p.estado === 'Completo') {
                    status.completed++;
                  } else if (p.estado === 'Incompleto') {
                    status.incompleted++;
                  }
                });
              });
            });
            return status;
        }

        verifyProjects() {
          var projects = 0;
          this.jurisdiccion.objetivosJurisdiccionales.forEach((oj) => {
            oj.objetivosOperativos.forEach((oo) => {
              projects = projects + oo.proyectos.length;
            });
          });
          if (projects === 0 && this.$state.current.name === 'home.tree'
            && ((!(<any>this.$state.params).fromState) || (<any>this.$state.params).fromState !== 'home')) {
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

        goToElement(element) {
          var form = document.getElementsByTagName(element);
          (<any>$('html,body')).animate({
            scrollTop: $(form).offset().top - 80},
          'slow');
        }

        presentAllProjects() {
          this.services.presentAllProject(this.localStorageService.get(this.idjurisdiccionKey)).then((data) => {
            this.services.deferNotification('Los proyectos fueron presentados correctamente');
            this.$state.reload();
          });
        }

        canEdit(proyecto) {
          return proyecto.estado !== 'En Priorización' && proyecto.estado !== 'Borrador' && proyecto.estado !== 'Pre Aprobado Completo'
          && !(this.rolUser === 'Operador de jurisdicción' && (proyecto.estado === 'Demorado' || proyecto.estado === 'Rechazado' || proyecto.estado === 'Aprobado'
          || proyecto.estado === 'D. Presentado')) && !(this.rolUser === 'Secretaría' && (proyecto.estado === 'Completo' || proyecto.estado === 'Incompleto'));
        }

    }
}

export = Home;
