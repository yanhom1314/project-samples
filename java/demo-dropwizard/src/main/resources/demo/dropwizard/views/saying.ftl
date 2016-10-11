<#-- @ftlvariable name="" type="demo.dropwizard.views.SayingView" -->
<html>
<body>
<!-- calls getPerson().getName() and sanitizes it -->
<h1>Hello, ${saying.id?html}!</h1>
<h1>Hello, ${person?if_exists.id}!</h1>
</body>
</html>