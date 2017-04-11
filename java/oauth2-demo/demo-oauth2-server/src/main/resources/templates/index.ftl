<#import "layout/boot.ftl" as p/>
<@p.boot "Hahaha" ; section>
    <#if section = "head">
    <h1>Hello World!!!</h1>
    <h2>reqeust:${request?string}</h2>
    </#if>
    <#if section = "body">
    <!-- Just another example of using a macro: -->
        <@p.otherExample p1=1 p2=2 />
    </#if>
</@p.boot>
