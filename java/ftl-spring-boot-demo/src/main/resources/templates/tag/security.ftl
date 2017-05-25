<#macro security>
    <#--
    ${SPRING_SECURITY_CONTEXT.authentication.principal.username}<br/>
    ${SPRING_SECURITY_CONTEXT.authentication.details}<br/>
    <#list SPRING_SECURITY_CONTEXT.authentication.authorities as t>
        <h5>${t}</h5>
    </#list>
    -->
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
    ${SPRING_SECURITY_CONTEXT.authentication.name}<br/>
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
