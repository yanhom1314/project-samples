<#include "common/boot.ftl"/>
<@layout "TITLE" "hello">
<div>
    <form action="/login" method="POST">
        <input type="text" name="username" value=""><br/>
        <input type="password" name="password" value=""><br/>
        <button type="submit">登录</button>
    </form>
    <#if error??>
        <p style="color: red">${error}</p>
    </#if>
</div>
</@layout>