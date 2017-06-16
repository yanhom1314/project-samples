<#include "../common/fragment.ftl"/>
<#macro layout title menu>
<!DOCTYPE html>
<html data-clipboard-buttons="" data-code-prettify="" data-code-sidebar="" data-hide-show-guide="" data-sts-import="" data-mobile-support="" data-search="">
<head>
    <meta http-equiv=Content-Type content="text/html;charset=utf-8">
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta content=always name=referrer>
    <title>${title!"NOT SET TITLE"}</title>
    <@head/>
</head>
<body>
<h1>The menu is ${menu!}.</h1>
<div style="background-color: darkgray">
    <#nested/>
</div>
</body>
</html>
</#macro>