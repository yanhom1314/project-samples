const webpack = require('webpack');

module.exports = {
    entry: {
        app1:'./public/dev/app1.js',
        app2:'./public/dev/app2.js'
    },
    output: {
        path: './public/javascripts',
        filename: '[name].js',
    },
    module: {
        loaders: [
            { test: /\.css$/, loader: 'style-loader!css-loader' },
            { test: /\.js$/, loader: 'jsx-loader?harmony' },
            { test: /\.scss$/, loader: 'style!css!sass?sourceMap' },
            { test: /\.(jpe?g|png|gif)$/, loader: 'url-loader?limit=8192' }
        ]
    },
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('main.js'),
        /**
        new webpack.optimize.UglifyJsPlugin({
            compress: {
                warnings: false,
            },
            output: {
                comments: false,
            },
        }),
        */
    ],
    //其它解决方案配置
    resolve: {
        alias: {
            'main.css' : '../css/main.css',
            'math' : './lib/math.js'
        }
    }
};