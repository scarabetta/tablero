var sharedConfig = require("./base.karma.conf");

module.exports = function (config) {
    var conf = sharedConfig(config);

    conf.set({
        reporters: ['dots', 'junit', 'coverage'],
        singleRun: true,
        autoWatch: false,
        junitReporter: {
            outputFile: 'test-results.xml'
        },
        coverageReporter: {
            type: 'cobertura'
        },
        browsers: ['PhantomJS']
    });

};
