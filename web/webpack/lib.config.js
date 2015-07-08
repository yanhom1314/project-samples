var path = require("path");
module.exports = {
    entry: {
        math:'./lib/math.js',
        inc:'./lib/inc.js'
    },
    output: {
        path: path.join(__dirname, "html/lib"),
        filename: "[name].js",
        library: ["lyf", "[name]"],
        libraryTarget:'var'
    },
    externals: {
       'jquery': 'jQuery',
       'math':'math'
    },
    module: {
        loaders: [
            { test: /\.css$/, loader:'style!css'},
            { test: /\.(png|jpg|gif)$/, loader: 'url-loader?limit=8192'},
            { test: /\.(woff|ttf|eot|woff2|svg)$/, loader: "file"}
        ]
    }
};
