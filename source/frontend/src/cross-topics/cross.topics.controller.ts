import {GeneralServices} from "../services/services.ts";
import {TemaTransversal} from "../models/jurisdiccion";

module CrossTopics {

    export class CrossTopicsController {

      private crossTopics: TemaTransversal[];

      /*@ngInject*/
      constructor(private services:GeneralServices, private $state: ng.ui.IStateService, private $scope:ng.IScope, private $compile: ng.ICompileService) {
        services.getTemasTransversales().then((data) => {
          this.crossTopics = data;
        });
      }

      addCrossTopic() {
        if (!angular.element(document.getElementsByTagName('crosstopicform')).length) {
          var referralDivFactory = this.$compile(" <crosstopicform></crosstopicform> ");
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById('newcrosstopic');
          angular.element(containerDiv).append(referralDiv);
        }
      }

      editCrossTopic(id) {
        console.log(id);
        if (!angular.element(document.getElementsByTagName('crosstopicform')).length) {
          var referralDivFactory = this.$compile(" <crosstopicform currentcrosstopicid='" + id + "'></crosstopicform> ");
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById("newcrosstopic");
          angular.element(containerDiv).append(referralDiv);
        }
      }

      toggleCrossTopicState(crossTopic) {
        this.services.toggleCrossTopicState(crossTopic);
      }

      addAlert(data) {
        var formDiv = document.getElementsByTagName('alertmodal');
        angular.element(formDiv).remove();
        var referralDivFactory = this.$compile(" <alertmodal title='" + data.title + "' text='" + data.text + "' callback='crossTopicsCtrl." + data.callback + "(" + data.id + ")'></alertmodal> ");
        var referralDiv = referralDivFactory(this.$scope);
        var containerDiv = document.getElementById('alertmodalcomponent');
        angular.element(containerDiv).append(referralDiv);
      }

      deleteCrossTopicById(id) {
        this.services.deleteTemaTransversal(id).then((data) => {
            this.$state.reload();
        });
      }

      deleteCrossTopic(id) {
        var dataAlert = {
          title: "Aviso",
          text: "Se va a eliminar el tema transversal. ¿Continuar?",
          callback: 'deleteCrossTopicById',
          id: id
        };
        this.addAlert(dataAlert);
      }
    }
}

export = CrossTopics;
