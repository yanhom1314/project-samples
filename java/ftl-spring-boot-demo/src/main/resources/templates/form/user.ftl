<#include "../layout/main.ftl"/>
<@layout "User">
<div class="alert alert-info">
    <form action="<@s.url "/form/user"/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <@s.bind "object.id"/>
        <input type="hidden" name="${s.status.expression}" value="${s.status.value}"/>
        <@s.bind "object.name" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="Name"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <@s.bind "object.age" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="Age"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>

        <input type="submit" value="Submit"/>
    </form>

    <#if item??>
        <h3>${item.name!} | ${item.age}</h3>
    </#if>
</div>
</@layout>
