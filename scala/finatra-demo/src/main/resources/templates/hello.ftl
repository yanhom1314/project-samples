<#include "common/public.ftl">
<h1>Hello World!</h1>
<h3>${name?if_exists}</h3>
<div>
</div>
<#list persons as t>
<li>${t.name()}|${t.age()}|${t.address()}</li>
<li><@greet person=t/></li>
</#list>
