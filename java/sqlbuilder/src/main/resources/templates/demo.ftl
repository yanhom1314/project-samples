<#include "layout/boot.ftl"/>
<@boot "Index">
<h1>Hello World!</h1>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="m1" style="width: 600px;height:400px;float: left"></div>

<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="m2" style="width: 600px;height:400px;float: right"></div>

<script src="<@s.url "/resources/js/app1.min.js"/>"></script>
<script src="<@s.url "/resources/js/app2.min.js"/>"></script>
</@boot>