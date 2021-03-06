const webpack = require('webpack');
const path = require('path');

module.exports = {
    plugins: [
        new webpack.optimize.CommonsChunkPlugin('main'),
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
        app1: './public/js/dev/app1.js',
        app2: './public/js/dev/app2.js',
        app3: './public/js/dev/app3.js',
        flash: './public/js/dev/flash.js'
    },
    output: {
        path:  path.resolve(__dirname, './public/js'),
        filename: '[name].min.js'
    },  
    module: {
      rules: [
        {
          test: /\.js$/,
          exclude: /(node_modules|bower_components)/,
          use: {
            loader: 'babel-loader',
            options: {
              presets: ['env']
            }
          }
        }
      ]
    },  
    //其它解决方案配置
    resolve: {
        alias: {
            //'main-css': '../../css/main.css',
            //'bootstrap-css': '../../../node_modules/bootstrap/dist/css/bootstrap.min.css',
            //'jquery-ui-css': '../../../node_modules/jquery-ui/themes/base/all.css',
            //'free-jqgrid-css': '../../../node_modules/free-jqgrid/css/ui.jqgrid.min.css',
            'free-jqgrid-locale-cn': '../../../node_modules/free-jqgrid/js/i18n/min/grid.locale-cn.js',
            //'datatables-css': '../../../node_modules/datatables/media/css/jquery.dataTables.min.css'
        }
    }
};
