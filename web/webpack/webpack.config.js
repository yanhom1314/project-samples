var path = require("path");
module.exports = {
    entry: {
        semantic:'./semantic-load.js',
        d_c3:"./demo_c3.js",
        d_amd:"./demo_amd.js",
        d_jquery:"./demo_jquery.js",
        d_terminal:"./demo_terminal.js",

    },
    output: {
        path: path.join(__dirname, "html/js"),
        filename: "[name].bundle.js"
    },
    externals: {
       'jquery': 'jQuery'
      //'d3':'d3'
    },
    module: {
        loaders: [
            { test: /\.css$/, loader:'style!css'},
            { test: /\.(png|jpg|gif)$/, loader: 'url-loader?limit=8192'},
            { test: /\.(woff|ttf|eot|woff2|svg)$/, loader: "file"}
        ]
    }
};
