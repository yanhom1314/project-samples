<#import "../layout/main.ftl" as p/>
<#import "../tag/security.ftl" as s/>
<@p.layout "Hahaha">
<div class="alert alert-info">
    ${session.getAttribute("SPRING_SECURITY_CONTEXT").authentication.details}<br/>
    ${session.getAttribute("SPRING_SECURITY_CONTEXT").authentication.principal}<br/>
    <hr/>
    <@s.security/>
</div>
</@p.layout>
