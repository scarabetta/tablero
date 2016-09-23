import {GeneralServices} from "../services/services.ts";
import {Search} from "../services/search.ts";
import {Jurisdiccion} from "../models/jurisdiccion.ts";
import {Usuario} from "../models/jurisdiccion.ts";

module Menu {

    export class MenuItem {
        public state: string;
        public title: string;
        public controllerAs: string;
        public permission: string;
    }

    export class MenuController {

        private items:MenuItem[];
        private jurisdiccion:Jurisdiccion[];
        private idjurisdiccionKey = 'idJurisdiccionStorage';
        private isWaitingJurisdicciones: boolean;
        private searchTextModel: string;
        private currentUserKey = 'currentUser';
        /*@ngInject*/
        constructor(private $state: ng.ui.IStateService, private services:GeneralServices, private search:Search, private localStorageService:angular.local.storage.ILocalStorageService) {
            this.items = [
                {"state": "home.tree", "title": "Alta de proyectos", "controllerAs": "homeCtrl", "permission": null},
                {"state": "users", "title": "Usuarios", "controllerAs": "usersCtrl", "permission": "Gestion de usuarios"},
                {"state": "priorization", "title": "Priorización", "controllerAs": "priorizationCtrl", "permission": "Gestion de Temas Transversales"},
                {"state": "cross", "title": "Temas Transversales", "controllerAs": "crossTopicsCtrl", "permission": "Gestion de priorizacion"}
            ];
            this.isWaitingJurisdicciones = false;
        }

        isLoginView() {
          if (this.$state.current.name !== 'login') {
            if (!this.jurisdiccion && !this.isWaitingJurisdicciones) {
              this.isWaitingJurisdicciones = true;
              this.services.jurisdicciones().then((data) => {
                if (data) {
                  this.jurisdiccion = data;
                  this.setIdJurisdiccion();
                  this.isWaitingJurisdicciones = false;
                }
              });
            }
            return true;
          } else {
            return false;
          }
        }

        showItem(item) {
            var userData = this.localStorageService.get(this.currentUserKey);
            var user = <Usuario>userData;
            if (item.permission === null) {
              return true;
            }
            var retVal = false;
            if (user) {
              user.roles.forEach((rol) => {
                rol.permisosEntidad.forEach((permiso) => {
                  if (permiso.nombre === item.permission && permiso.gestion) {// tslint:disable-line max-line-length
                    retVal = true;
                  }
                });
              });
            }
            return retVal;
        }

        setIdJurisdiccion() {
          if (!this.localStorageService.get(this.idjurisdiccionKey) && this.jurisdiccion) {
            this.localStorageService.set(this.idjurisdiccionKey, this.jurisdiccion[0].idJurisdiccion);
            this.$state.reload();
          }
        }

        logout() {
          this.services.logout();
        }

        currentJurisdiccion(id) {
          return this.localStorageService.get(this.idjurisdiccionKey) === id;
        }

        changeJurisdiccion(id) {
            this.localStorageService.set(this.idjurisdiccionKey, id);
            if (this.$state.current.name === 'home.tree') {
              this.$state.reload();
            } else {
              this.$state.go('home.tree');
            }
        }

        applySearch() {
          this.search.setText(this.searchTextModel);
        }

    }

    export let menuComponent =  {
       template: `<nav  class="navbar navbar-default" role="navigation" ng-if="$ctrl.isLoginView()">
                   <div class="contentMenu">
                    <div class="container contentApp">
                        <div class="row">
                            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                              <ul class="nav navbar-nav">
                                <li ui-sref="{{item.state}}" ng-repeat="item in $ctrl.items" ng-class="{active: $ctrl.$state.current.controllerAs==item.controllerAs}" ng-show="$ctrl.showItem(item)">
                                    <a>{{item.title}}</a>
                                </li>
                                <li ng-if="$ctrl.jurisdiccion.length > 1" class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Jurisdicci&oacute;n <b class="caret"></b></a>
                                    <ul class="dropdown-menu multi-column columns-3">
                                      <div class="row">
                                          <ul class="multi-column-dropdown">
                                            <div class="col-sm-6" ng-repeat="item in $ctrl.jurisdiccion | orderBy:'abreviatura'">
                                              <li><a ng-click="$ctrl.changeJurisdiccion(item.idJurisdiccion)">
                                              {{item.abreviatura}}
                                              <i ng-if="$ctrl.currentJurisdiccion(item.idJurisdiccion)" class="glyphicon glyphicon-ok icJurisdiccion"></i>
                                              </a>
                                              </li>
                                            </div>
                                          </ul>
                                      </div>
                                    </ul>
                                </li>
                              </ul>
                              <ul class="nav navbar-nav navbar-right">
                              <li class="separador">|</li>
                                <li><a class="menuExit" ng-click="$ctrl.logout()">Salir</a></li>
                              </ul>
                              <form class="navbar-form navbar-right" role="search">
                                <div class="form-group">
                                  <div class="icLupa"></div>
                                  <input ng-model="$ctrl.searchTextModel" class="form-control" placeholder="Buscar en la Jurisdicción" type="text">
                                </div>
                                <button ng-click="$ctrl.applySearch()" type="submit" class="btn btn-default">Buscar</button>
                              </form>
                            </div>
                        </div>
                    </div>
                  </div>
                 </nav>
                <div class="jumbotron jumbotron-misc jumbotron-main" ng-if="$ctrl.isLoginView()">
                 <jurisdiccionheader></jurisdiccionheader>
               </div>`,
        controller: MenuController
    };

}

export = Menu;
