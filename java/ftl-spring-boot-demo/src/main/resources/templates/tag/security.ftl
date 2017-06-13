<#macro security>
<ul>
    <li>${SPRING_SECURITY_CONTEXT.authentication.principal.username}</li>
    <li>${SPRING_SECURITY_CONTEXT.authentication.details}</li>
    <#list SPRING_SECURITY_CONTEXT.authentication.authorities as t>
        <li>${t}</li>
    </#list>
</ul>
</#macro>
<#macro isAuth>
    <#if SPRING_SECURITY_CONTEXT??>
        <#nested/>
    </#if>
</#macro>
<#macro isNotAuth>
    <#if !SPRING_SECURITY_CONTEXT??>
        <#nested/>
    </#if>
</#macro>
<#macro username>
<h4>${SPRING_SECURITY_CONTEXT.authentication.name}</h4>
</#macro>
<#macro hasRole role>
    <#if SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains(role)>
        <#nested/>
    </#if>
</#macro>
<#macro notHasRole role>
    <#if SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains(role)>
    <#else>
        <#nested/>
    </#if>
</#macro>
