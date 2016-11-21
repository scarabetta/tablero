var webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var CopyWebpackPlugin = require('copy-webpack-plugin');
var ngAnnotatePlugin = require('ng-annotate-webpack-plugin');
var yargs = require('yargs');
var argv = yargs.choices('env', ['development', 'testing', 'production', 'jsonserver', 'qa', 'devasi', 'devrel']).default('env', 'development').argv;


module.exports = {
    entry: {
        app: __dirname + '/src/index.ts'
    },

    output: {
        path: __dirname + '/build',
        filename: 'bundle.js'
    },

    resolve: {
        extensions: ['', '.webpack.js', '.web.js', '.ts', '.js'],
        alias: {
            "app-config": __dirname + "/app-config.js",
            "bootstrap.css" : __dirname + "/node_modules/bootstrap/dist/css/bootstrap.css",
            "bootstrap.js" : __dirname + "/node_modules/bootstrap/dist/js/bootstrap.min.js",
            "proyectosba": __dirname + "/assets/styles/proyectosBA.css",
            "circle": __dirname + "/assets/styles/circle.css",
            "BAstrap.css": __dirname + "/assets/styles/bastrap3/bastrap.css",
            "usig.autocompleter.full.js": __dirname + "/assets/usig-3.1/usig.AutoCompleterFull.min.js",
            "usig.autocompleter.dialog.css": __dirname + "/assets/usig-3.1/css/usig.AutoCompleterDialog.bootstrap.css",
            "usig.recorridos.js": __dirname + "/assets/usig-3.1/usig.RecorridosFull.min.js",
            "usig.mapainteractivo.js": __dirname + "/assets/usig-3.1/usig.MapaInteractivo.min.js",
            "angular-touch.js" : __dirname + "/node_modules/angular-touch/angular-touch.min.js",
            "angular-carousel.js" : __dirname + "/assets/angular-carousel/angular-carousel.js",
            "angular-carousel.css" : __dirname + "/assets/angular-carousel/angular-carousel.css",
            "isteven-multi-select.js" : __dirname + "/assets/isteven-multi-select.js",
            "isteven-multi-select.css" : __dirname + "/assets/isteven-multi-select.css",
            "ng-onboarding.js" : __dirname + "/assets/ngOnboarding/ng-onboarding.js",
            "ng-onboarding.css" : __dirname + "/assets/ngOnboarding/ng-onboarding.css",
            "loader.js" : __dirname + "/assets/loader/loadingoverlay.min.js",
            "checklist-model.js" : __dirname + "/node_modules/checklist-model/checklist-model.js",
            "ng-tags-input.js" : __dirname + "/node_modules/ng-tags-input/build/ng-tags-input.js",
            "ng-tags-input.css" : __dirname + "/node_modules/ng-tags-input/build/ng-tags-input.css",
            "angular-animate.js" : __dirname + "/node_modules/angular-animate/angular-animate.min.js",
            "angular-validator.min.js" : __dirname + "/assets/angular-validator.min.js",
            "pin.js" : __dirname + "/assets/jquery.pin.min.js",
            "jquery.sidr.min.js" : __dirname + "/node_modules/sidr/dist/jquery.sidr.min.js",
            "handsontable.full.css" : __dirname + "/node_modules/handsontable/dist/handsontable.full.css",
            "handsontable.full.js" : __dirname + "/node_modules/handsontable/dist/handsontable.full.js",
            "ngHandsontable.js" : __dirname + "/node_modules/ng-handsontable/dist/ngHandsontable.min.js",
            "chart.js" : __dirname + "/node_modules/chart.js/dist/Chart.min.js",
            "angular-chart" : __dirname + "/node_modules/angular-chart.js/dist/angular-chart.min.js"
        }
    },

    module: {
        loaders: [
            {
                test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/,
                loader: "url-loader?limit=10000&minetype=application/font-woff"
            },
            {test: /\.(ttf|otf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: "file-loader"},
            {test: /\.(png|eot|ttf|svg)$/, loader: 'url-loader?limit=100000'},
            {test: /\.html$/, loader: 'ngtemplate!html-loader'},
            {test: /\.ts$/, loaders: ['ts-loader'], exclude: [/node_modules/, /test/]}
        ]
    },

    plugins: [
        new HtmlWebpackPlugin({
            title: 'Gobierno de la Ciudad de Buenos Aires',
            template: 'index.html',
            favicon: __dirname + "/assets/favicon.ico"
        }),
        new webpack.ProvidePlugin({
            jQuery: 'jquery',
            $: 'jquery',
            jquery: 'jquery',
            _: 'lodash',
            Handsontable: 'handsontable',
            numbro: 'numbro'
        }),
        new CopyWebpackPlugin([
            {from: "assets/usig-3.1/images", to: "images"},
            {from: "assets/usig-3.1/css", to: "css"},
            {from: "assets/loader/images", to: "images"},
            {from: "assets/imgEjes", to: "images"},
            {from: "assets/montserrat", to: "fonts"}
        ]),
        new webpack.DefinePlugin({
            __VERSION__: JSON.stringify(require('./package').version)
        })
    ],

    loader: {
        /*
         This data is used by the webpack-config-loader
         */
        configEnvironment: argv.env
    }
};
