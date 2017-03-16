var webpack = require('webpack');
var path = require('path');
var commonsPlugin = new webpack.optimize.CommonsChunkPlugin('common');

module.exports = {
    //插件项
    plugins: [commonsPlugin],
    //页面入口文件配置
    entry: {        
        demo : './js/demo.js'
    },
    //入口文件输出配置
    output: {
        path: path.resolve(__dirname, 'dist/js'),        
        filename: '[name].min.js'
    },
    module: {
        //加载器配置
        loaders: [
            { test: /\.css$/, loader: 'style-loader!css-loader' },
            { test: /\.js$/, loader: 'jsx-loader?harmony' },
            { test: /\.scss$/, loader: 'style!css!sass?sourceMap'},
            { test: /\.(png|jpg)$/, loader: 'url-loader?limit=8192'}
        ]
    },
    externals: {
        // require("jquery") 是引用自外部模块的
        // 对应全局变量 jQuery
        "jQuery": "jQuery"        
    }
};