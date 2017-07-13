import {d3} from "./lib";
import $ from "jquery";
import echarts from "echarts";

console.log(d3._add(11, 12));
console.log($("#accordion")._add(11, 12));

// 基于准备好的dom，初始化echarts实例
var m1 = echarts.init(document.getElementById('m1'));
// 绘制图表
m1.setOption({
    title: {text: 'ECharts 入门示例'},
    tooltip: {},
    xAxis: {
        data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: [5, 20, 36, 10, 10, 20]
    }]
});
var m2 = echarts.init(document.getElementById('m2'));
// 显示标题，图例和空的坐标轴
m2.setOption({
    title: {
        text: '异步数据加载示例'
    },
    tooltip: {},
    legend: {
        data:['销量']
    },
    xAxis: {
        data: []
    },
    yAxis: {},
    series: [{
        name: '销量',
        type: 'bar',
        data: []
    }]
});

// 异步加载数据
$.get('/dnslog/resources/json/data.json').done(function (data) {
    // 填入数据
    m2.setOption({
        xAxis: {
            data: data.categories
        },
        series: [{
            // 根据名字对应到相应的系列
            name: '销量',
            data: data.data
        }]
    });
});
