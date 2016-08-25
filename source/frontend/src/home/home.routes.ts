import {IStateProvider} from "angular-ui-router";
import {HomeController} from "./home.controller.ts";
const templateUrl = require('./home.html');
const templateUrlTree = require('./home-tree.html');
const templateUrlUpload = require('./home-upload.html');

module Home {

    /*@ngInject*/
    export function routes($stateProvider:IStateProvider) {
        $stateProvider
            .state('home', {
                url: '/home',
                templateUrl: templateUrl,
                controller: HomeController,
                controllerAs: 'homeCtrl',
                parent: 'root',
                data: {
                  requireLogin: true
                }
            })
            .state('home.tree', {
                url: '/home/tree',
                templateUrl: templateUrlTree,
                controller: HomeController,
                controllerAs: 'homeCtrl',
                parent: 'root',
                data: {
                  requireLogin: true
                }
            })
            .state('home.upload', {
                url: '/home/upload',
                templateUrl: templateUrlUpload,
                controller: HomeController,
                controllerAs: 'homeCtrl',
                parent: 'root',
                data: {
                  requireLogin: true
                }
            });

    }
}

export = Home;
