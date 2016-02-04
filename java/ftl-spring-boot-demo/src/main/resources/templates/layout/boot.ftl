<#macro boot title>
<!DOCTYPE html>
<html>
<head>
    <title>${title?if_exists}</title>
    <script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
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
