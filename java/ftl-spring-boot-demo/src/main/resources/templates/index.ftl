<#import "layout/boot.ftl" as p/>
<@p.boot "Hahaha" ; section>
    <#if section = "head">
    <h1>Hello World!!!</h1>
    </#if>
    <#if section = "body">
    <!-- Just another example of using a macro: -->
        <@p.otherExample p1=11 p2=22 />
    </#if>
</@p.boot>
