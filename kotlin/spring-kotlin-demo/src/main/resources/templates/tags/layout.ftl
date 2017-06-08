<#import "/spring.ftl" as s/>
<#macro layout title menu>
<!DOCTYPE html>
<html data-clipboard-buttons="" data-code-prettify="" data-code-sidebar="" data-hide-show-guide="" data-sts-import="" data-mobile-support="" data-search="">
<head>
    <meta http-equiv=Content-Type content="text/html;charset=utf-8">
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta content=always name=referrer>
    <title>${title!"NOT SET TITLE"}</title>

    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>

    <script src="//cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/vue/2.3.3/vue.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h1>The menu is ${menu!}.</h1>
<div style="background-color: darkgray">
    <#nested/>
</div>
</body>
</html>
</#macro>