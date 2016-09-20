import {GeneralServices} from "../../services/services.ts";
import {TemaTransversal} from "../../models/jurisdiccion.ts";
const template = require('./form-cross-topic.html');

module Home {

  export class CrossTopicFormController {

    private title: string;
    private currentCrossTopic: TemaTransversal;
    private currentcrosstopicid: number;

    /*@ngInject*/
    constructor(private services:GeneralServices, private $state:ng.ui.IStateService) {
      if (!this.currentcrosstopicid) {
        this.title = "Nuevo tema transversal";
        this.currentCrossTopic = <TemaTransversal>{};
      } else {
        services.getTemaTransversal(this.currentcrosstopicid).then((data) => {
          this.currentCrossTopic = data;
          console.log(this.currentCrossTopic);
        });
        this.title = "Modificar tema transversal";
      }
    }

    submit() {
      (this.currentCrossTopic.idTemaTransversal ? this.services.updateTemaTransversal(this.currentCrossTopic) : this.services.saveTemaTransversal(this.currentCrossTopic))
      .then((data) => {
        this.$state.reload();
      });
    }

    cancel() {
      var formDiv = document.getElementsByTagName('crosstopicform');
      angular.element(formDiv).remove();
    }
  }

  export let crossTopicsFormComponent = {
      bindings: {
        currentcrosstopicid: '@'
      },
      templateUrl: template,
      controller: CrossTopicFormController,
      controllerAs: 'formCtrl',
  };

}

export = Home;
