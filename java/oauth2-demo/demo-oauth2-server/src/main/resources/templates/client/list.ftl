<#import "../layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Client create">
<div class="row-fluid">
    <#if clientList??>
        <ul>
            <#list clientList as t>
                <li><a href="<@s.url '/authorize?client_id=${t.clientId!}&response_type=code&redirect_uri=http://aimeizi.net'/>">${t.clientName} ${t.clientSecret}</a></li>
            </#list>
        </ul>
    </#if>
</div>
</@p.layout>