<#include "common/public.ftl">
<#assign menu="m_info"/>

<@layout ;section>
    <#if section = "head">
    <div><h1>Hello World:你好!</h1></div>
    <#elseif section = "content">
    <h3>
        Current User:${user.principal}
    </h3>
    <h3>
        isAuthenticated:${user.isAuthenticated()?string}
    </h3>
    <h3>
        Session Timeout:${user.session.timeout}
    </h3>
    <h3>
        Session Timeout:${user.session.lastAccessTime?datetime}
    </h3>
    </#if>
</@layout>

