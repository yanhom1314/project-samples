<#include "../layout/main.ftl"/>
<@layout "User">
<div class="alert alert-info">
    <@s.username/>
    <@s.security/>
    <@s.hasRole "ROLE_ADMIN">
        <h1>ROLE_ADMIN!!!</h1>
    </@s.hasRole>
    <@s.notHasRole "ROLE_ADMIN">
        <h1>NOT HAS ROLE_ADMIN!!!</h1>
    </@s.notHasRole>
    <@s.hasRole "ROLE_USER">
        <h1>ROLE_USER!!!</h1>
    </@s.hasRole>
    <@s.notHasRole "ROLE_USER">
        <h1>NOT HAS ROLE_USER!!!</h1>
    </@s.notHasRole>
    <@s.notHasRole "ROLE_OTHER">
        <h1>NOT HAS ROLE_OTHER!!!</h1>
    </@s.notHasRole>
</div>
</@layout>
