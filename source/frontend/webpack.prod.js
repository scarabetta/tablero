var webpackBase = require('./webpack.base');
var webpack = require('webpack');
var ExtractTextPlugin = require("extract-text-webpack-plugin");

var config = Object.create(webpackBase);

config.module.loaders = webpackBase.module.loaders.concat([
    {test: /\.css$/, loader: ExtractTextPlugin.extract("style-loader", "css-loader")}
]);

config.plugins = webpackBase.plugins.concat([
    new ExtractTextPlugin("styles.css")
]);

module.exports = config;
