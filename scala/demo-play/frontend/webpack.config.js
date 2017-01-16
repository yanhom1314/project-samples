const webpack = require('webpack');

module.exports = {
    plugins: [
        //new webpack.optimize.CommonsChunkPlugin('common.min.js'),
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
        index: './js/dev/index.js',
        demo: './js/dev/demo.js'
    },
    output: {
        path: './js/',
        filename: '[name].min.js'
    },
    //其它解决方案配置
    resolve: {
        alias: {
            'fetch': 'fetch'
        }
    },
    externals: {
        Vue: true
    }
};
