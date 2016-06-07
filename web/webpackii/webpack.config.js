var webpack = require('webpack');
var commonsPlugin = new webpack.optimize.CommonsChunkPlugin('common.js');
var uglifyJsPlugin = new webpack.optimize.UglifyJsPlugin({
    compress: {
        warnings: false
    }
});

module.exports = {
    //插件项
    plugins: [commonsPlugin,uglifyJsPlugin],
    //plugins: [commonsPlugin],
    //页面入口文件配置
    entry: {
        index: './src/js/page/index.js',
        main: './src/js/page/main.js',
        jqgrid: './src/js/page/jqgrid.js',
    },
    //入口文件输出配置
    output: {
        path: 'dist/js/page',
        filename: '[name].js'
    },
    module: {
        //加载器配置
        loaders: [
            { test: /\.css$/, loader: 'style-loader!css-loader' },
            { test: /\.js$/, loader: 'jsx-loader?harmony' },
            { test: /\.scss$/, loader: 'style!css!sass?sourceMap' },
            { test: /\.(jpe?g|png|gif)$/, loader: 'url-loader?limit=8192' }
        ]
    },
    // externals: {
    //     'jquery': 'jQuery',
    //     'echarts': 'echarts',
    //     'bootstrap':true
    // },
    //其它解决方案配置
    resolve: {
        //root: 'e:/tmp/spring-boot', //绝对路径
        extensions: ['', '.js', '.json', '.scss'],
        alias: {
            css: '../../../src/css/main.css',
            grid: '../../../src/js/jqgrid/demo.js',
            math: '../../../src/js/lib/math.js',
            form: '../../../src/js/form/form.js',
            draw: '../../../src/js/echarts/draw.js'
        }
    }
};