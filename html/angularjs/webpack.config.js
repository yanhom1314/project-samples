//npm install css-loader file-loader style-loader url-loader
var path = require("path");
module.exports = {
    entry: {
        controller:'./js/controller.js'
    },
    output: {
        path: path.join(__dirname, "dist"),
        filename: "[name].bundle.js"
    },
    // externals: {
    //    'angularjs': 'angularjs'
    // },
    module: {
        loaders: [
            { test: /\.css$/, loader:'style!css'},
            { test: /\.(png|jpg|gif)$/, loader: 'url-loader?limit=8192'},
            { test: /\.(woff|ttf|eot|woff2|svg)$/, loader: "file"}
        ]
    }
};
