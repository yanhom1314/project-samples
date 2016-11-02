<#include "common/public.ftl">
<#assign menu="m_login"/>

<@layout ;section>
    <#if section = "head">
    <div><h1>登录页面</h1></div>
    <#elseif section = "content" >
    <div>
        <form action="/login" method="POST">
            <input type="text" name="username" value=""><br/>
            <input type="password" name="password" value=""><br/>
            <button type="submit">登录</button>
        </form>
    </div>
        <#if error??>
        <p style="color: red">${error}</p>
        </#if>
    <#else>
    <div>Unsupported section??</div>
    </#if>
</@layout>