import {GeneralServices} from "../services/services.ts";
import {Usuario} from "../models/jurisdiccion";

module Users {

    export class UsersController {

      private usuarios: Usuario[];

      /*@ngInject*/
      constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private $scope:ng.IScope, private $compile: ng.ICompileService) {
        services.getUsers().then((data) => {
          this.usuarios = data;
        });
      }

      addUser() {
        if (!angular.element(document.getElementsByTagName('userform')).length) {
          var referralDivFactory = this.$compile(" <userform></userform> ");
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById('newuser');
          angular.element(containerDiv).append(referralDiv);
        }
      }

      editUser(id) {
        console.log(id);
        if (!angular.element(document.getElementsByTagName('userform')).length) {
          var referralDivFactory = this.$compile(" <userform currentuserid='" + id + "'></userform> ");
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById("newuser");
          angular.element(containerDiv).append(referralDiv);
        }
      }

      toggleUserState(user) {
        this.services.toggleUserState(user);
      }

      deleteUserById(id) {
        this.services.deleteUser(id).then((data) => {
            this.$state.reload();
        });
      }

      goToElement(idElement) {
          (<any>$('html,body')).animate({
            scrollTop: $("#" + idElement).offset().top},
          500);
      }

    }
}

export = Users;
