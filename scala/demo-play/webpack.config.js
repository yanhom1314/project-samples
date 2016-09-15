const webpack = require('webpack');

module.exports = {
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('main.js'),
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false,
            },
            output: {
                comments: false,
            }
        })
    ],
    entry: {
        app1: './public/javascripts/app1.js',
        app2: './public/javascripts/app2.js'
    },
    output: {
        path: './public/javascripts',
        filename: '[name].min.js',
    },
    module: {
        loaders: [
            {test: /\.css$/, loader: 'style-loader!css-loader'},
            {test: /\.js$/, loader: 'jsx-loader?harmony'},
            {test: /\.scss$/, loader: 'style!css!sass?sourceMap'},
            {test: /\.(eot|woff|woff2|ttf|svg|jpe?g|png|gif)$/, loader: 'url-loader?limit=8192'}
        ]
    },
    //其它解决方案配置
    resolve: {
        alias: {
            'main-css': '../css/main.css',
            'bootstrap-css': '../../node_modules/bootstrap/dist/css/bootstrap.min.css',
            'math': './lib/math.js'
        }
    }
};
