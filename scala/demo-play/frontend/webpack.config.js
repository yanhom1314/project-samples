const webpack = require('webpack');
var path = require('path');

module.exports = {
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('common'),
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
        index: './dev/index.js',
        demo: './dev/demo.js',
        form: './dev/form.js',
        loader: './dev/loader.js'
    },
    output: {
        path: path.resolve(__dirname, './js'),
        publicPath: '/js/',
        filename: '[name].min.js'
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    loaders: {
                        // Since sass-loader (weirdly) has SCSS as its default parse mode, we map
                        // the "scss" and "sass" values for the lang attribute to the right configs here.
                        // other preprocessors should work out of the box, no loader config like this nessessary.
                        'scss': 'vue-style-loader!css-loader!sass-loader',
                        'sass': 'vue-style-loader!css-loader!sass-loader?indentedSyntax'
                    }
                    // other vue-loader options go here
                }
            },
            {
                test: /\.js$/,
                loader: 'babel-loader',
                //exclude: /node_modules/,
            },
            {
                test: /\.(png|jpg|gif|svg)$/,
                loader: 'file-loader',
                options: {
                    name: '[name].[ext]?[hash]'
                }
            }
        ]
    },
    devServer: {
        historyApiFallback: true,
        noInfo: true
    },
    devtool: '#eval-source-map',
    //其它解决方案配置
    resolve: {
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
