<#import "layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Index">
<div class="panel">
    <h1>Hello World!!!</h1>
    <h2>reqeust:${request?string}</h2>
    <h3>username:${_s_username!}</h3>
</div>
</@p.layout>
