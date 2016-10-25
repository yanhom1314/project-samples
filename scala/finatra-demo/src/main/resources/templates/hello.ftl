<#include "common/public.ftl">
<#assign menu="m_hello"/>

<@layout ; section>
    <#if section = "head">
    <div><h1>Hello World:你好!</h1></div>
    <#elseif section = "content" >
    <h3>${name?if_exists}</h3>
    <div>
        <#list persons as t>
            <li>${t.name()}|${t.age()}|${t.address()}</li>
            <li><@greet person=t/></li>
        </#list>
    </div>
    <button class="btn btn-danger">搞什么飞机</button>
    <#else>
    <div>Unsupported section??</div>
    </#if>
</@layout>

