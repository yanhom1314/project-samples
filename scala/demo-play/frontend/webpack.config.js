const webpack = require('webpack');
var path = require('path');

module.exports = {
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('common.min.js'),
        // new webpack.optimize.UglifyJsPlugin({
        //     compress: {
        //         warnings: false,
        //     },
        //     output: {
        //         comments: false,
        //     }
        // })
    ],
    entry: {
        index: './js/dev/index.js',
        demo: './js/dev/demo.js',
        form: './js/dev/form.js'
    },
    output: {
        path: './js/',
        filename: '[name].min.js'
    },
    module: {
        loaders: [
            {
                test:/\.js$/,
                loader: 'babel-loader',
                query: {
                    presets: ['es2015']
                }
            }
        ]
    },
    //其它解决方案配置
    resolve: {
        extensions: ['', '.js', '.json', '.coffee'],
        alias: {
            'vee-locale-cn': '../../node_modules/vee-validate/dist/locale/zh_CN'
        }
    },
    externals: {
        vue: "Vue",
        'vee-validate': "VeeValidate",
        fetch: "fetch",
        jquery: "jQuery"
    }
};
