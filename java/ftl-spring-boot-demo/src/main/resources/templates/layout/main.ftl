<#include "fragment.ftl"/>
<#macro layout title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title!"NOT SET TITLE!!!"}</title>
    <@head/>
</head>
<body>
    <#nested/>
</body>
</html>
</#macro>

