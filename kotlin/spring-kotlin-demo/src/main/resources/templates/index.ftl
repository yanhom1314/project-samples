<#include "tags/layout.ftl"/>
<@layout "Hello World" "index">
<div class="btn-group" role="group">
    <button type="button" class="btn btn-default">Left</button>
    <button type="button" class="btn btn-default">Middle</button>
    <button type="button" class="btn btn-default">Right</button>
    <button class="btn btn-default">${message!"Hello Kotlin World!"}</button>
    <a href="<@s.url "/home"/>" class="btn btn-primary">Home</a>
</div>
</@layout>
