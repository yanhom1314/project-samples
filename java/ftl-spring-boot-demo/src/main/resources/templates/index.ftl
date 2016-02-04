<#import "layout/boot.ftl" as p/>
<@p.boot "Hahaha" ; section>
    <#if section = "head">
    <h1>Hello World!!!</h1>
    </#if>
    <#if section = "body">
    <!-- Just another example of using a macro: -->
        <@p.otherExample p1=111 p2=222 />
    </#if>
</@p.boot>
