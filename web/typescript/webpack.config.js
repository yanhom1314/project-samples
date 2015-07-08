
var path = require("path");
module.exports = {
    entry: {
        test:"./test.js",

    },
    output: {
        path: path.join(__dirname, "dist"),
        filename: "[name].bundle.js"
    },
    module: {
        loaders: [
            { test: /\.css$/, loader:'style!css'},
            { test: /\.(png|jpg|gif)$/, loader: 'url-loader?limit=8192'},
            { test: /\.(woff|ttf|eot|woff2|svg)$/, loader: "file"}
        ]
    }
}
