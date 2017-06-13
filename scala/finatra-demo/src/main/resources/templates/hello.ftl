<#include "common/public.ftl">
<#assign menu="m_hello"/>

<@layout ;section>
    <#if section = "head">
    <div><h1>Hello World:你好!</h1></div>
    <#elseif section = "content" >
    <h3>${name!}</h3>
    <div>
        <#list persons as t>
            <li>${t.name}|${t.age}|${t.address}|${t.friends?size}</li>
            <#list t.friends as f>
                <span>[${f.name},${f.age}]</span>
            </#list>
        </#list>
    </div>
    <button class="btn btn-danger">搞什么飞机</button>
    <#else>
    <div>Unsupported section??</div>
    </#if>
</@layout>

