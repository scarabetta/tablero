import {IUrlRouterProvider} from "angular-ui-router";
import {ILocationProvider} from "angular";
import {IStateProvider} from "angular-ui-router";
const templateHeader = require('../views/ba.header.html');

/*@ngInject*/
export default function routing($urlRouterProvider:IUrlRouterProvider, $locationProvider:ILocationProvider,
                                $stateProvider:IStateProvider) {
    $locationProvider.html5Mode(true);
    /*@ngInject*/
    $urlRouterProvider.otherwise(function($injector, $location) {
        var $state = $injector.get("$state");
        $state.go("login");
    });
    $stateProvider
        .state('root', {
            abstract: true,
            views: {
                'baheader@' : {
                    templateUrl: templateHeader
                }
            }
        });
}
