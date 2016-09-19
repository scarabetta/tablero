const config = require('webpack-config-loader!app-config');
import * as angular from 'angular';
import 'proyectosba';
import 'circle';
import 'angular-ui-router';
import 'angular-ui-bootstrap';
import 'angular-animate';
import 'angular-recaptcha';
import 'ng-file-upload';
import 'angular-local-storage';
import 'angular-carousel';
import 'angular-touch';
import 'angular-animate.js';
import 'angular-validator.min.js';
import routes from './app.routes.ts';
import routesInterceptor from './app.routes.interceptor.ts';
import authInterceptor from './app.auth.interceptor.ts';
import localStorageConfig from './app.local.storage.config.ts';
import {home} from '../home/home.module.ts';
import {login} from '../login/login.module.ts';
import {users} from '../users/users.module.ts';
import {priorization} from '../priorization/priorization.module.ts';
import {upload} from '../upload/upload.module.ts';
import {preview} from '../upload/preview/preview.module.ts';
import {results} from '../upload/results/results.module.ts';
import {crossTopics} from '../cross-topics/cross.topics.module.ts';
import {menuComponent} from '../menu/menu.component.ts';
import {excelComponent} from '../excel/excel.component.ts';
import {excelButton} from '../excel/excel.component.ts';
import {notificationDirective} from '../notification/notification.component.ts';
import {jurisdiccionHeaderComponent} from '../home/jurisdiccion.header.component.ts';
import {versionComponent} from './app.version.component.ts';

/*@ngInject*/
let initInjector = angular.injector(['ng']);
let $http: any = initInjector.get('$http');
$http.get(config.authBaseUrl + 'config/properties').then(
    function (response) {
        let configModule = angular.module('config', []).constant('urlsConfig', response.data);
        angular.module('app', ['ui.router', 'ui.bootstrap', 'ngAnimate', 'vcRecaptcha', 'ngFileUpload', 'angularValidator',
        'isteven-multi-select', 'LocalStorageModule', 'angular-carousel', 'checklist-model', 'ngTagsInput', 'ngTouch', home, login, users,
        priorization, crossTopics, upload, preview, results, configModule.name])
            .config(routes)
            .config((tagsInputConfigProvider) => {
              tagsInputConfigProvider
                .setActiveInterpolation('tagsInput', { placeholder: true })
                .setDefaults('tagsInput', { placeholder: '' });
            })
            .config(localStorageConfig)
            .config(authInterceptor)
            .directive('notification', notificationDirective)
            .component('attversion', versionComponent)
            .component('navigationmenu', menuComponent)
            .component('excelcomponent', excelComponent)
            .component('excelbutton', excelButton)
            .component('jurisdiccionheader', jurisdiccionHeaderComponent)
            .filter('capitalize', function() {
              return function(input) {
                return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
              };
            })
            .run(routesInterceptor);

        angular.element(document).ready(function() {
            angular.bootstrap(document, ['app']);
        });

    }
).catch(error => console.log('Error al cargar la configuracion inicial'));
