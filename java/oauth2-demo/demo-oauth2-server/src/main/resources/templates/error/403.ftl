<#import "../layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Error Page!">
<div class="row-fluid">
    <div class="panel">
        <h1>${error!}</h1>
    </div>
</div>
</@p.layout>
