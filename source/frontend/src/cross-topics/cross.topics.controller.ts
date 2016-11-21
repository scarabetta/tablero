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
        let notifications = this.services.loadDeferredNotifications();
        notifications.forEach(n => this.appendNotification(n));
      }

      private appendNotification(data): void {
            var referralDivFactory = this.$compile(' <notification type="' + data.type + '" icon="' + data.icon + '" title="' + data.title + '" text="' + data.text + '"></notification> '); // tslint:disable-line max-line-length
            var referralDiv = referralDivFactory(this.$scope);
            var containerDiv = document.getElementById('notifications');
            angular.element(containerDiv).append(referralDiv);
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
            this.services.deferNotification('El Tema Transversal se eliminó con éxito.');
            this.$state.reload();
        });
      }
    }
}

export = CrossTopics;
