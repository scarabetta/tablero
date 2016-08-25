require('webpack-config-loader!app-config');

module Version {

    declare var __VERSION__ : string;

    export class VersionController {

      private versionFrontend : string;

      /*@ngInject*/
      constructor(private $http:ng.IHttpService) {
        this.versionFrontend = __VERSION__;
      }

    }

    export let versionComponent =  {
        template: `<div id="version-frontend">v{{ $ctrl.versionFrontend }}</div>`,
        controller: VersionController
    };

}
export = Version;
