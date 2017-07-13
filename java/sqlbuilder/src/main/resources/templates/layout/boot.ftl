<#import "/spring.ftl" as s/>
<#macro boot title>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
    <meta http-equiv="expires" content="0"/>
    <title>${title!"NOT SET TITLE!!!"}</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="<@s.url "/resources/css/main.css"/>"/>
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="<@s.url "/resources/js/main.min.js"/>"></script>
</head>
<body>
<#-- message -->
    <#if success??>
    <script>
        $(function () {
            $.messager.alert('成功消息', '${success}');
        });
    </script>
    </#if>
    <#if error??>
    <script>
        $(function () {
            $.messager.alert('失败消息', '${error!}', 'error');
        });
    </script>
    </#if>
<#-- body -->
    <#nested/>
</body>
</html>
</#macro>
