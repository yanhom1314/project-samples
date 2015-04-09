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
