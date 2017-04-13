<#import "layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Index">
<div class="panel">
    <h1>All users loop!</h1>
    <#if all??>
        <#list all as t>
            <li>${t.username!}</li>
        </#list>
    </#if>
</div>
</@p.layout>
