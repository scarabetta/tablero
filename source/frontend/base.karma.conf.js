var webpackTestConfig = require("./webpack.test");
var path = require("path");


// Karma configuration here
module.exports = function (config) {
    config.set({
        logLevel: config.LOG_DEBUG,
        port: 3334,
        browsers: ['Chrome'],
        singleRun: true, //just run once by default
        frameworks: ['mocha', 'chai'], //use jasmine as framework
        files: [
            path.join(__dirname, 'node_modules', 'phantomjs-polyfill', 'bind-polyfill.js'), // To enable PhantomJS to render React components
            'karma.tests.js' //test files
        ],
        preprocessors: {
            'karma.tests.js': ['webpack', 'sourcemap'] //preprocess with webpack and sourcemap loader
        },
        reporters: ['progress'], //report results in this format
        webpack: webpackTestConfig,
        webpackMiddleware: {
            stats: {
                chunkModules: false,
                colors: true
            }
        }
    });
    return config;
};