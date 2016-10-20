const template = require('./sidemenu.html');
import {GeneralServices} from "../services/services.ts";
import {Jurisdiccion} from "../models/jurisdiccion.ts";

module Home {

  export class SidemenuController {

    private filterObject = [
      {
        'title' : 'Estado',
        'options' : new Array<any>(),
        'value' : 'estado'
      },
      {
        'title' : 'Área',
        'options' : new Array<any>(),
        'value' : 'area'
      },
      {
        'title' : 'Eje de gobierno',
        'options' : new Array<any>(),
        'value' : 'ejesDeGobierno'
      },
      {
        'title' : 'Tema transversal',
        'options' : new Array<any>(),
        'value' : 'temasTransversales'
      },
      {
        'title' : 'Segmentos de la población',
        'options' : new Array<any>(),
        'value' : 'poblacionesMeta'
      },
      {
        'title' : 'Prioridad jurisdiccional',
        'options' : new Array<any>(),
        'value' : 'prioridadJurisdiccional'
      },
      // {
      //   'title' : 'Presupuesto solicitado GCBA',
      //   'options' : new Array<any>(),
      //   'value' : ''
      // },
      // {
      //   'title' : 'Fecha',
      //   'options' : new Array<any>(),
      //   'value' : '',
      //   'method' : ''
      // },
      {
        'title' : 'Comunas',
        'options' : new Array<any>(),
        'value' : 'comunas'
      }
    ];
    private allProjects = new Array<any>();
    private filterData: {
      estado: any[];
      area: any[];
      ejesDeGobierno: any[];
      temasTransversales: any[];
      poblacionesMeta: any[];
      prioridadJurisdiccional: any[];
      comunas: any[];
    };
    private jurisdiccion:Jurisdiccion;
    private idjurisdiccionKey = 'idJurisdiccionStorage';

    constructor(private $scope: ng.IScope, private services:GeneralServices, private localStorageService:angular.local.storage.ILocalStorageService) {
      var scope = this;

      var allStates  = new Array<any>();
      var allAreas  = new Array<any>();
      var allEjes  = new Array<any>();
      var allTemas  = new Array<any>();
      var allPoblaciones  = new Array<any>();
      var allPrioridad  = new Array<any>();
      var allComunas  = new Array<any>();

      var idJurisdiccionStorage = this.localStorageService.get(this.idjurisdiccionKey);
      if (idJurisdiccionStorage) {
        services.getJurisdiccion(idJurisdiccionStorage).then((data) => {
          scope.jurisdiccion = data;
          data.objetivosJurisdiccionales.forEach((oj) => {
            oj.objetivosOperativos.forEach((oo) => {
              oo.proyectos.forEach((project) => {
                this.allProjects.push(project);
              });
            });
          });

          scope.allProjects.forEach((project) => {
            allStates.push(project.estado);
            if (project.area) {
              allAreas.push(project.area.nombre);
            }
            if (project.ejesDeGobierno) {
              project.ejesDeGobierno.forEach((eje) => {
                allEjes.push(eje.nombre);
              });
            }
            if (project.temasTransversales) {
              project.temasTransversales.forEach((tema) => {
                allTemas.push(tema.temaTransversal);
              });
            }
            if (project.poblacionesMeta) {
              project.poblacionesMeta.forEach((poblacion) => {
                allPoblaciones.push(poblacion.nombre);
              });
            }
            if (project.prioridadJurisdiccional) {
              allPrioridad.push(project.prioridadJurisdiccional);
            }
            if (project.comunas) {
              project.comunas.forEach((comuna) => {
                allComunas.push(comuna.nombre);
              });
            }
          });
          scope.filterObject[0].options = (<any>$).unique( allStates );
          scope.filterObject[1].options = (<any>$).unique( allAreas );
          scope.filterObject[2].options = (<any>$).unique( allEjes );
          scope.filterObject[3].options = (<any>$).unique( allTemas );
          scope.filterObject[4].options = (<any>$).unique( allPoblaciones );
          scope.filterObject[5].options = (<any>$).unique( allPrioridad );
          scope.filterObject[6].options = (<any>$).unique( allComunas );
        });
      };
    }

    clearFilters() {
      (<any>$(".filterCheckBox")).each(function() {
        if ((<any>$(this)).prop("checked")) {
          setTimeout(() => {
            (<any>$(this)).trigger("click");
          }, 0);
        }
      });
    }

    filter(value) {
      var scope = this;

      if (scope.filterData[value].length === 0) {
          delete scope.filterData[value];
      }

      scope.jurisdiccion.objetivosJurisdiccionales.forEach((oj, i) => {
        oj.objetivosOperativos.forEach((oo, j) => {

          var fisrtFilter = true;
          var projectFromFilter;

          if (Object.keys(scope.filterData).length === 0) {

              projectFromFilter = [];
              scope.jurisdiccion.objetivosJurisdiccionales[i].objetivosOperativos[j].proyectos.forEach((pj, k) => {
                  projectFromFilter.push(pj);
              });

          } else {

            (<any>$).each(scope.filterData, function(key, val) {
              var filters = key;

              if (fisrtFilter) {
                fisrtFilter = false;
                projectFromFilter = [];
                oo.proyectos.forEach((pj, k) => {

                  if (filters === 'estado' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      if (pj[filters] === value) {
                        projectFromFilter.push(pj);
                      }
                    });
                  }

                  if (filters === 'area' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      if (pj[filters].nombre === value) {
                        projectFromFilter.push(pj);
                      }
                    });
                  }

                  if (filters === 'ejesDeGobierno' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((eje) => {
                        if (eje.nombre === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }

                  if (filters === 'temasTransversales' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((tema) => {
                        if (tema.temaTransversal === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }

                  if (filters === 'poblacionesMeta' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((poblacion) => {
                        if (poblacion.nombre === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }

                  if (filters === 'prioridadJurisdiccional' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      if (pj[filters] === value) {
                        projectFromFilter.push(pj);
                      }
                    });
                  }

                  if (filters === 'comunas' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((comuna) => {
                        if (comuna.nombre === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }
                });
              } else {
                var reFilter = projectFromFilter;
                projectFromFilter = [];
                reFilter.forEach((pj, k) => {
                  if (filters === 'estado' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      if (pj[filters] === value) {
                        projectFromFilter.push(pj);
                      }
                    });
                  }

                  if (filters === 'area' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      if (pj[filters].nombre === value) {
                        projectFromFilter.push(pj);
                      }
                    });
                  }

                  if (filters === 'ejesDeGobierno' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((eje) => {
                        if (eje.nombre === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }

                  if (filters === 'temasTransversales' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((tema) => {
                        if (tema.temaTransversal === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }

                  if (filters === 'poblacionesMeta' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((poblacion) => {
                        if (poblacion.nombre === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }

                  if (filters === 'prioridadJurisdiccional' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      if (pj[filters] === value) {
                        projectFromFilter.push(pj);
                      }
                    });
                  }

                  if (filters === 'comunas' && pj[filters]) {
                    scope.filterData[filters].forEach((value) => {
                      pj[filters].forEach((comuna) => {
                        if (comuna.nombre === value) {
                          projectFromFilter.push(pj);
                        }
                      });
                    });
                  }

                });
              }


            });
          }

          (<any>this.$scope.$parent).homeCtrl.jurisdiccion.objetivosJurisdiccionales[i].objetivosOperativos[j].proyectos = (<any>$).unique( projectFromFilter );

        });
      });

    }

  }

  /*@ngInject*/
  export function sideMenuDirective() {
    return {
      restrict: 'E',
      templateUrl: template,
      scope: {},
      controller: SidemenuController,
      controllerAs: 'sidemenuCtrl'
    };
  };
}

export = Home;
