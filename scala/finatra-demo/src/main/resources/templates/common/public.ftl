<#macro greet person>
<h5 style="color: blue">Hello menu:[${menu!"nothing"}] ${person?if_exists.name()}!${person?if_exists.age()}!${person?if_exists.address()}!</h5>
</#macro>

<#macro layout>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/3.1.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<h1>Menu:[${menu!"not set menu in page:<#assign menu='menu_id'/>"}]</h1>
<div class="container-fluid">
    <div id="head" class="row"><#nested "head" /></div>
    <div id="content" class="row"><#nested "content" /></div>
</div>
</body>
</html>
</#macro>