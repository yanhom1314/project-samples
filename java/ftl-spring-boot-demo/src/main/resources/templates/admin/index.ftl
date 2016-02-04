<#import "../layout/main.ftl" as p/>
<@p.layout "Hahaha">
<div class="alert alert-info">
    ${session.getAttribute("SPRING_SECURITY_CONTEXT").authentication.details}<br/>
    ${session.getAttribute("SPRING_SECURITY_CONTEXT").authentication.principal}<br/>
        Authorities
        getCredentials
</div>
</@p.layout>
