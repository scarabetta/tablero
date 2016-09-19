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
    constructor(private services:GeneralServices, private $state:ng.ui.IStateService) {
      console.log('New user form');

      if (!this.currentuserid) {
        this.title = "Nuevo usuario";
        this.currentuser = {
            "idUsuario": null,
            "activo": false,
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
          console.log(this.currentuser);
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

    loadTags($query) {
        return this.jurisdicciones.filter(function(tag) {
          return tag.nombre.toLowerCase().indexOf($query.toLowerCase()) !== -1;
        });
    }

    submit() {
      console.log(this.currentuser);
      (this.currentuser.idUsuario ? this.services.updateUser(this.currentuser) : this.services.saveUser(this.currentuser))
      .then((data) => {
        this.$state.reload();
      });
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
