<#macro security>
${session.getAttribute("SPRING_SECURITY_CONTEXT").authentication.details}<br/>
${session.getAttribute("SPRING_SECURITY_CONTEXT").authentication.principal}<br/>
</#macro>