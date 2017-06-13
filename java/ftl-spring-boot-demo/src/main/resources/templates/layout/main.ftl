<#import "/spring.ftl" as s/>
<#import "../tag/security.ftl" as e/>
<#macro layout title>
<!DOCTYPE html>
<html>
<head>
    <title>${title!"NOT SET TITLE!!!"}</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<@s.url "/resources/css/standard.css"/>">
    <script src="//cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>
    <#nested/>
</body>
</html>
</#macro>

