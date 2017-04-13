<#import "layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Index">
<div class="panel">
    <h1>Hello World!!!</h1>
    <h2>request:${request?string}</h2>
    <h3>username:${_s_username!}</h3>
    <ul>
        <li><a href="<@s.url '/client'/>" class="btn btn-primary" target="_blank">认证授权</a></li>
        <li><a href="<@s.url '/login'/>" class="btn btn-primary" target="_blank">登录</a></li>
        <li><a href="<@s.url '/logout'/>" class="btn btn-primary" target="_blank">登出</a></li>
        <li><a href="<@s.url '/cache'/>" class="btn btn-primary" target="_blank">缓存</a></li>
        <li><a href="<@s.url '/cache/update'/>" class="btn btn-primary" target="_blank">缓存-更新</a></li>
    </ul>
    <#if all??>
        <#list all as t>
            <li>${t.username!}</li>
        </#list>
    </#if>
</div>
</@p.layout>
