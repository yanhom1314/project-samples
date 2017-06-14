var webpack = require('webpack');
var path = require('path');

module.exports = {
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('common'),
        new webpack.optimize.UglifyJsPlugin({
             compress: {
                 warnings: false
             }
        })
    ],
    entry: {
        form: './src/main/resources/static/resources/js/dev/form.js',
        person: './src/main/resources/static/resources/js/dev/person.js'
        //index: './src/main/resources/static/resources/js/dev/index.js',
        //loader: './src/main/resources/static/resources/js/dev/loader.js'
    },
    output: {
        path: path.resolve(__dirname, './src/main/resources/static/resources/js/dist'),
        publicPath: '/js/dist/',
        filename: '[name].min.js'
    },
    module: {
        rules: [
            {
                test: /\.vue$/,
                loader: 'vue-loader',
                options: {
                    loaders: {}
                    // other vue-loader options go here
                }
            },
            {
                test: /\.js$/,
                loader: 'babel-loader',
                exclude: /node_modules/
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
    resolve: {
        alias: {
            vue: 'vue/dist/vue.min.js',
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    devServer: {
        historyApiFallback: true,
        noInfo: true
    },
    performance: {
        hints: false
    },
    externals: {
       jQuery: "jQuery"
    }
};
