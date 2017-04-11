<#import "../layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Client create">
<div class="row-fluid">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">Panel heading</div>
        <!-- Table -->
        <#if clientList??>
            <table class="table">
                <thead>
                <tr>
                    <td>应用标识(client_id)</td>
                    <td>应用密钥(client_secret)</td>
                    <td>应用名称</td>
                </tr>
                </thead>
                <#list clientList as t>
                    <tr>
                        <td>
                            <a href="<@s.url '/authorize?client_id=${t.clientId!}&response_type=code&redirect_uri=/index'/>" target="_blank">${t.clientId} </a>
                        </td>
                        <td>${t.clientSecret}</td>
                        <td>${t.clientName}</td>
                    </tr>
                </#list>
            </table>
        </#if>
    </div>

    <div class="panel">
        <div class="panel-heading">认证</div>
        <div class="input-group">
            <input id="access_token" type="text" class="form-control" placeholder="access_token">
            <span class="input-group-btn">
                <button id="go" class="btn btn-default" target="_blank">Go!</button>
            </span>
        </div><!-- /input-group -->
        <script>
            $(function () {
                $("#go").click(function () {
                    var url = "http://localhost:8000/v1/openapi/user?access_token=" + $("#access_token").value();
                    console.log("url:" + url);
                    window.open(url);
                });
            });
        </script>
    </div>

    <div class="panel">
        <div class="panel-heading">授权</div>
        <div class="input-group">
            <input id="access_token" type="text" class="form-control" placeholder="access_token">
            <span class="input-group-btn">
                <button id="go" class="btn btn-default" target="_blank">Go!</button>
            </span>
        </div><!-- /input-group -->
        <script>
            $(function () {
                $("#go").click(function () {
                    var url = "http://localhost:8000/v1/openapi/user?access_token=" + $("#access_token").value();
                    console.log("url:" + url);
                    window.open(url);
                });
            });
        </script>
    </div>
    <div class="panel">
        <div class="panel-heading">授权</div>
        <div class="input-group">
            <input id="access_token" type="text" class="form-control" placeholder="access_token">
            <span class="input-group-btn">
                <button id="go" class="btn btn-default" target="_blank">Go!</button>
            </span>
        </div><!-- /input-group -->
        <script>
            $(function () {
                $("#go").click(function () {
                    var url = "http://localhost:8000/v1/openapi/user?access_token=" + $("#access_token").value();
                    console.log("url:" + url);
                    window.open(url);
                });
            });
        </script>
    </div>
</div>
</@p.layout>