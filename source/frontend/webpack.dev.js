var webpackBase = require('./webpack.base');
var webpack = require('webpack');

var config = Object.create(webpackBase);

config.devtool = 'source-map';

config.module.loaders = webpackBase.module.loaders.concat([
    {test: /\.css$/, loader: "style-loader!css-loader"}
]);

config.plugins = webpackBase.plugins.concat([new webpack.HotModuleReplacementPlugin()]);

config.devServer = {
    historyApiFallback: true,
    hot: true,
    inline: true,
    progress: true,
    host: '0.0.0.0'
};

module.exports = config;
