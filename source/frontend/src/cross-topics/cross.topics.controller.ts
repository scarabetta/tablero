import {GeneralServices} from "../services/services.ts";
import {TemaTransversal} from "../models/jurisdiccion";

module CrossTopics {

    export class CrossTopicsController {

      private crossTopics: TemaTransversal[];
      private idForDelete: number;

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
        if (!angular.element(document.getElementsByTagName('crosstopicform')).length) {
          var referralDivFactory = this.$compile(" <crosstopicform currentcrosstopicid='" + id + "'></crosstopicform> ");
          var referralDiv = referralDivFactory(this.$scope);
          var containerDiv = document.getElementById("newcrosstopic");
          angular.element(containerDiv).append(referralDiv);
        }
      }

      topicForDelete(id) {
        this.idForDelete = id;
      }

      toggleCrossTopicState(crossTopic) {
        this.services.toggleCrossTopicState(crossTopic);
      }

      deleteCrossTopicById() {
        this.services.deleteTemaTransversal(this.idForDelete).then((data) => {
            this.$state.reload();
        });
      }
    }
}

export = CrossTopics;
