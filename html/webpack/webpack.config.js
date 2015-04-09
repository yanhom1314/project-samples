module.exports = {
    entry: "./entry.js",
    output: {
        path: __dirname,
        filename: "html/bundle.js"
    },
    externals: {
      'jQuery': 'jQuery'
    },
    module: {
        loaders: [
            { test: /\.css$/, loader:'style!css'},
            { test: /\.(png|jpg|gif)$/, loader: 'url-loader?limit=8192'}
        ]
    }
};
