<#macro greet person>
<h5 style="color: blue">Hello ${person?if_exists.name()}!${person?if_exists.age()}!${person?if_exists.address()}!</h5>
</#macro>