const webpack = require('webpack');

module.exports = {
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('main.min.js'),
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
        app1: './public/javascripts/dev/app1.js',
        app2: './public/javascripts/dev/app2.js',
        app3: './public/javascripts/dev/app3.js',
        flash: './public/javascripts/dev/flash.js'
    },
    output: {
        path: './public/javascripts',
        filename: '[name].min.js'
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
            'main-css': '../../css/main.css',
            'bootstrap-css': '../../../node_modules/bootstrap/dist/css/bootstrap.min.css',
            'jquery-ui-css': '../../../node_modules/jquery-ui/themes/base/all.css',
            'free-jqgrid-css': '../../../node_modules/free-jqgrid/css/ui.jqgrid.min.css',
            'free-jqgrid-locale-cn': '../../../node_modules/free-jqgrid/js/i18n/ming/grid.locale-cn.js',
            'datatables-css': '../../../node_modules/datatables/media/css/jquery.dataTables.min.css',
            'math': '../lib/math.js'
        }
    }
};
