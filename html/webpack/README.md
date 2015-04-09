## 安装

        npm install webpack -g
        npm install style-loader css-loader url-loader
        #npm install jquery d3 semantic-ui
        npm install d3 semantic-ui

## C3

        cd c3
        wget https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.js
        wget https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.css

## 结构
+ demo_jquery.js:

        require("./style.css");

        var content = require("./content.js");
        document.write(content.msg);
        content.sayHello();

+ content.js:

        var $ = require("jquery");
        module.exports = {
            msg:"It works from content.js.",
            sayHello:function(){
                console.log("Hello!"+$("body").html());
            }
        };

+ demo_c3.js:

        //css
        require("./c3/c3.css");
        require("semantic-ui-css/semantic.css");
        //js
        require("semantic-ui-css/semantic.js");
        var c3 = require("./c3/c3.js");
        //proc
        var chart = c3.generate({
            bindto: '#chart',
            data: {
              x:'x',
              columns: [
                 ['x', '2013-01-01', '2013-01-02', '2013-01-03', '2013-01-04', '2013-01-05', '2013-01-06','2013-01-07','2013-01-08'],
                 ['data1', 30, 200, 100, 400, 150, 250,110,210,96],
                 ['data2', 50, 20, 10, 40, 15, 25,120,38,49]
              ]
            },
            axis: {
                x: {
                    type: 'timeseries',
                    tick: {
                        format: '%d'
                        //format: '%Y-%m-%d-%H'
                    }
                }
            }
        });
        setTimeout(function () {
            chart.load({
                columns: [
                    ['data3', 400, 500, 450, 700, 600, 500,340,198]
                ]
            });
        }, 1000);

+ html/jquery.html:

        <!doctype html>
        <html>
            <head>
                <meta charset="utf-8">
            </head>
            <body>
                <script type="text/javascript" src="//cdn.jsdelivr.net/jquery/2.1.3/jquery.min.js" charset="utf-8"></script>
                <script type="text/javascript" src="js/d_jquery.bundle.js" charset="utf-8"></script>
            </body>
        </html>

+ html/c3.html:

        <!doctype html>
        <html>
            <head>
                <meta charset="utf-8">
                <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.js" charset="utf-8"></script>
            </head>
            <body>
                <div id="chart" style="width:100%;"></div>
                <script type="text/javascript" src="js/d_c3.bundle.js" charset="utf-8"></script>
            </body>
        </html>

## jQuery
+ `<script type="text/javascript" src="//cdn.jsdelivr.net/jquery/2.1.3/jquery.min.js" charset="utf-8"></script>`
+ `webpack.config.js`中配置`externals`：jquery:'jQuery'
