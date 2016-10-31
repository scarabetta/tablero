const config = require('webpack-config-loader!app-config');
import './app/app.module.ts';
import 'bootstrap.css';
import 'bootstrap.js';
import 'BAstrap.css';
import "angular-touch.js";
import "angular-carousel.js";
import "angular-carousel.css";
import "ng-tags-input.js";
import "ng-tags-input.css";
import "checklist-model.js";
import 'isteven-multi-select.js';
import 'isteven-multi-select.css';
import 'ng-onboarding.js';
import 'ng-onboarding.css';
import 'loader.js';
import 'pin.js';
import 'jquery.sidr.min.js';
import 'usig.autocompleter.full.js';
import 'usig.autocompleter.dialog.css';
import 'usig.recorridos.js';
import 'usig.mapainteractivo.js';
import 'handsontable.full.css';
import 'handsontable.full.js';
import 'ngHandsontable.js';
require("expose?$!jquery");
require("expose?jQuery!jquery");

/* tslint:disable:no-console */
console.info(`Configuration: api=${config.apiBaseUrl}`);
/* tslint:enable */
