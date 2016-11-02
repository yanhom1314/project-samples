<h1>Admin:${user.principal} ROLE_USER:${user.hasRole("ROLE_USER")?string} ROLE_ADMIN:${user.hasRole("ROLE_ADMIN")?string} </h1>

<#if user.hasRole("ROLE_USER")>
User has ROLE_USER.
</#if>
<#if user.hasRole("ROLE_ADMIN")>
User has ROLE_ADMIN.
</#if>