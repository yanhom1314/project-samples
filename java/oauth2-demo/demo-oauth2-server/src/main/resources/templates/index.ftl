<#import "layout/boot.ftl" as p/>
<@p.boot "Hahaha" ; section>
    <#if section = "head">
        <h1>Hello World!!!</h1>
        <h2>reqeust:${request}</h2>
        <hr/>
        <h2>session:${session.SPRING_SECURITY_CONTEXT?if_exists}</h2>
        <#list session?keys as t>
            <h5>${t} -> ${session[t]}</h5>
        </#list>
    </#if>
    <#if section = "body">
        <!-- Just another example of using a macro: -->
        <@p.otherExample p1=1 p2=2 />
    </#if>
</@p.boot>
