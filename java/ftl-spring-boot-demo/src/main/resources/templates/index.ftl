<#include "layout/boot.ftl"/>
<@boot "Hahaha" ; section>
<style>

</style>
    <#if section == "head">
    <h1>Hello World!!!</h1>
    <h2>request:${request}</h2>
    <h3><@s.message 'logout'/></h3>
    <hr/>
    <h3>session:${SPRING_SECURITY_CONTEXT?if_exists}</h3>
        <#if SPRING_SECURITY_CONTEXT??>
        <h3>登录名：${Session["SPRING_SECURITY_CONTEXT"].authentication.name}</h3>
        <h3>登录名：${SPRING_SECURITY_CONTEXT.authentication.name}</h3>
        <h3>登录名：${SPRING_SECURITY_CONTEXT.authentication.principal.username}</h3>
        <h4>登录角色：</h4>
        <div style="background-color: antiquewhite">
            <#list SPRING_SECURITY_CONTEXT.authentication.authorities as t>
            ${t!}<br/>
            </#list>
        </div>
        <div>
            <#if SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains('ROLE_ADMIN')>
                <h1>ROLE_ADMIN</h1>
            <#else>
                <h1>NOT ROLE_ADMIN</h1>
            </#if>
            <#if SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains('ROLE_USER')>
                <h1>ROLE_USER</h1>
            <#else>
                <h1>NOT ROLE_USER</h1>
            </#if>
            <#if SPRING_SECURITY_CONTEXT.authentication.authorities?seq_contains('ROLE_OTHER')>
                <h1 style="background-color: green">ROLE_OTHER</h1>
            <#else>
                <h1 style="background-color: red">NOT ROLE_OTHER</h1>
            </#if>
        </div>
        </#if>
        <@change_locale/>
    </#if>
    <#if section=="body">
    <!-- Just another example of using a macro: -->
        <@otherExample p1=1 p2=2 />
    </#if>
</@boot>

