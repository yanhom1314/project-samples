<#macro layout title>
<!DOCTYPE html>
<html>
<head>
    <title>${title?if_exists}</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="//cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap-theme.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/resources/css/standard.css">

    <script src="//cdn.bootcss.com/jquery/2.1.4/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/vue/1.0.13/vue.min.js"></script>
</head>
<body>
    <#if error??>
    <div class="row-fluid">
        <div class="panel">
            <h1 style="color:red;">${error!}</h1>
        </div>
    </div>
    </#if>
    <#if success??>
    <div class="row-fluid">
        <div class="panel">
            <h5 style="color:green;">${error!}</h5>
        </div>
    </div>
    </#if>
    <#nested/>
</body>
</html>
</#macro>

