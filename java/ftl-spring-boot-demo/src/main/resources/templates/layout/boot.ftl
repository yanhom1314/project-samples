<#include "fragment.ftl"/>
<#macro boot title>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${title!"NOT SET TITLE!!!"}</title>
    <@head/>
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
