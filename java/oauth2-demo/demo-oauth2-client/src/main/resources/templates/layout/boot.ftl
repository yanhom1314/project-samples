<#macro boot title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title?if_exists}</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/resources/css/standard.css">

    <script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/vue/1.0.13/vue.min.js"></script>
</head>
<body>
<section id="head">
    <#nested "head"/>
</section>
<section id="body">
    <#nested "body"/>
</section>
</body>
</html>
</#macro>

<#macro otherExample p1 p2>
<p>The parameters were: ${p1}, ${p2}</p>
</#macro>
