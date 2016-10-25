<#macro greet person>
<h5 style="color: blue">Hello menu:[${menu!"nothing"}] ${person?if_exists.name()}!${person?if_exists.age()}!${person?if_exists.address()}!</h5>
</#macro>

<#macro layout>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container-fluid">
    <div id="head" class="row"><#nested "head" /></div>
    <div id="content" class="row"><#nested "content" /></div>
</div>
</body>
</html>
</#macro>