<#macro head>
<h1>DEFAULT H1 HEADER PAGE!</h1>
</#macro>
<#macro foot>
<h1>DEFAULT H1 FOOTER PAGE!</h1>
</#macro>

<#macro layout title menu>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${title!"NO TITLE"}</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Menu:[${menu!"not set menu"}]</h1>
<div class="container-fluid">
    <div id="head" class="row"><@head/></div>
    <div id="content" class="row"><#nested/></div>
    <div id="foot" class="row"><@foot/></div>
</div>
</body>
</html>
</#macro>
