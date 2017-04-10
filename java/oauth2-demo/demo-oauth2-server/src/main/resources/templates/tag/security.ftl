<#macro security>
    <#--
    ${session.SPRING_SECURITY_CONTEXT.authentication.principal.username}<br/>
    ${session.SPRING_SECURITY_CONTEXT.authentication.details}<br/>
    <#list session.SPRING_SECURITY_CONTEXT.authentication.authorities as t>
        <h5>${t}</h5>
    </#list>
    -->
</#macro>
<#macro username>
    ${session.SPRING_SECURITY_CONTEXT.authentication.principal.username}<br/>
</#macro>
<#macro hasRole role>
    <#if session.SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains(role)>
        <#nested/>
    </#if>
</#macro>
<#macro notHasRole role>
    <#if session.SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains(role)>
    <#else>
        <#nested/>
    </#if>
</#macro>
