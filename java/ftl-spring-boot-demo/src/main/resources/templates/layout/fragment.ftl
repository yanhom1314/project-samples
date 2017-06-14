<#import "/spring.ftl" as s/>
<#import "../tag/security.ftl" as e/>
<#macro head>
<link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
<link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<@s.url "/resources/css/standard.css"/>">
<script src="//cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="<@s.url '/resources/js/dist/common.min.js'/>"></script>
</#macro>
<#macro change_locale>
<div id="change_locale">
    <h5>${locale!"NO LOCALE"}</h5>
    <select id="locales">
        <option value="zh_CN" <#if locale?string == "zh_CN">selected</#if>>中文</option>
        <option value="en_US" <#if locale?string == "en_US">selected</#if>>English</option>
        <option value="fr" <#if locale?string == "fr">selected</#if>>Français</option>
    </select>
    <script>
        $(function () {
            $("#locales").change(function () {
                var selectedOption = $('#locales').val();
                if (selectedOption != '') {
                    var url = window.location.href;
                    var pos = url.indexOf("?");
                    url = pos > 0 ? url.substr(0, pos) : url;
                    window.location.replace(url + '?lang=' + selectedOption);
                }
            });
        });
    </script>
</div>
</#macro>