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
import {menuComponent} from '../menu/menu.component.ts';
import {notificationComponent} from '../notification/notification.component.ts';
import {jurisdiccionHeaderComponent} from '../home/jurisdiccion.header.component.ts';
import {versionComponent} from './app.version.component.ts';

angular.module('app', ['ui.router', 'ui.bootstrap', 'ngAnimate', 'vcRecaptcha', 'ngFileUpload', 'angularValidator',
'isteven-multi-select', 'LocalStorageModule', 'angular-carousel', 'checklist-model', 'ngTagsInput', 'ngTouch', home, login])
    .config(routes)
    .config(localStorageConfig)
    .config(authInterceptor)
    .component('notification', notificationComponent)
    .component('attversion', versionComponent)
    .component('navigationmenu', menuComponent)
    .component('jurisdiccionheader', jurisdiccionHeaderComponent)
    .run(routesInterceptor);
