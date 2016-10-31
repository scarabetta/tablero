import {GeneralServices} from "../../services/services.ts";
import {Usuario} from "../../models/jurisdiccion";
import {Rol} from "../../models/jurisdiccion";
import {Jurisdiccion} from "../../models/jurisdiccion";
const template = require('./form-user.html');

module Home {

  export class UserFormController {

    private title: string;
    private currentuser: Usuario;
    private currentuserid: number;
    private roles: Rol[];
    private jurisdicciones: Jurisdiccion[];

    /*@ngInject*/
    constructor(private services:GeneralServices, private $state:ng.ui.IStateService, private $compile: ng.ICompileService, private $scope:ng.IScope) {

      if (!this.currentuserid) {
        this.title = "Nuevo usuario";
        this.currentuser = {
            "idUsuario": null,
            "activo": true,
            "apellido": "",
            "descripcion": "",
            "email": "",
            "jurisdicciones": null,
            "nombreUsuario": "",
            "nombre": "",
            "roles": new Array<Rol>()
        };
      } else {
        services.getUser(this.currentuserid).then((data) => {
          this.currentuser = data;
          $('#mail').prop('readonly', true);
        });
        this.title = "Modificar usuario";
      }
      services.getRoles().then((data) => {
        this.roles = data;
      });
      services.jurisdicciones().then((data) => {
          this.jurisdicciones = data;
      });
    }

    addNotification(data) {
      var formDiv = document.getElementsByTagName('notification');
      angular.element(formDiv).remove();
      var referralDivFactory = this.$compile(' <notification type="' + data.type + '" icon="' + data.icon + '" title="' + data.title + '" text="' + data.text + '" ' + data.action + '="'  + data.valueAction + '" textlink="' + data.textlink + '" callback="' + 'homeCtrl.' + data.callback + '"></notification> '); // tslint:disable-line max-line-length
      var referralDiv = referralDivFactory(this.$scope);
      var containerDiv = document.getElementById('notificationsUser');
      angular.element(containerDiv).append(referralDiv);
    }

    loadTags($query) {
        return this.jurisdicciones.filter(function(tag) {
          return tag.nombre.toLowerCase().indexOf($query.toLowerCase()) !== -1;
        });
    }

    submit() {
      if ((this.currentuser.roles[0].nombre === 'Operador de jurisdicción') && (this.currentuser.jurisdicciones === null || this.currentuser.jurisdicciones === undefined || this.currentuser.jurisdicciones.length === 0)) {// tslint:disable-line
        var notificationData = {
          "type" : "warning",
          "icon" : "exclamation-sign",
          "title" : "Error",
          "text" : "Es necesario que un usuario con perfil 'Operador de jurisdicción' tenga asociada al menos una Jurisdicción." // tslint:disable-line
        };
        this.addNotification(notificationData);
      } else {
        (this.currentuser.idUsuario ? this.services.updateUser(this.currentuser) : this.services.saveUser(this.currentuser))
        .then((data) => {
          this.$state.reload();
        });
      }
    }

    cancel() {
      var formDiv = document.getElementsByTagName('userform');
      angular.element(formDiv).remove();
    }

  }

  export let userFormComponent = {
      bindings: {
          currentuserid: '@'
      },
      templateUrl: template,
      controller: UserFormController,
      controllerAs: 'userFormCtrl',
  };

}

export = Home;
