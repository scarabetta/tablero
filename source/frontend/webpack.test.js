var webpackBase = require('./webpack.base');

var config = {
    devtool: 'inline-source-map',
    resolve: webpackBase.resolve,
    module: {
        loaders: [
            {test: /\.css$/, loader: 'null-loader'},
            {test: /\.less$/, loader: 'null-loader'},
            {test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'null-loader'},
            {test: /\.(ttf|eot|svg)(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: 'null-loader'},
            {test: /\.(png|eot|ttf|svg)$/, loader: 'null-loader'},
            {test: /\.html$/, loader: 'ngtemplate!html-loader'},
            {test: /\.ts$/, loader: 'ts-loader', exclude: [/node_modules/]}
        ]
    }
};

module.exports = config;
