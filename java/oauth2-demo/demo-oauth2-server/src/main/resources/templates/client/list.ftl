<#import "../layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Client create">
<div class="row-fluid">
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">
            <a href="<@s.url '/client/create'/>" class="btn btn-primary">创建应用</a>
        </div>
        <!-- Table -->
        <#if clientList??>
            <table class="table">
                <thead>
                <tr>
                    <td>应用标识(client_id)</td>
                    <td>应用密钥(client_secret)</td>
                    <td>应用名称</td>
                    <td>操作</td>
                </tr>
                </thead>
                <#list clientList as t>
                    <tr>
                        <td>${t.clientId}</td>
                        <td>${t.clientSecret}</td>
                        <td>${t.clientName}</td>
                        <td>
                            <a href="<@s.url '/authorize?client_id=${t.clientId!}&response_type=code&redirect_uri=/access'/>" target="_blank">认证授权</a><br/>
                            <a href="<@s.url '/access'/>" target="_blank">授权令牌-后台调用</a><br/>
                            <div class="panel">
                                <div class="panel-heading">授权令牌</div>
                                <div class="input-group">
                                    <input id="${t.id}_access_token" type="text" class="form-control" placeholder="access_token">
                                    <span class="input-group-btn"><button id="${t.id}_go" class="btn btn-default" target="_blank">Go!</button>
                                </span>
                                </div><!-- /input-group -->
                                <script>
                                    $(function () {
                                        $("#${t.id}_go").click(function () {
                                            var url = "/access_token=" + $("#${t.id}_access_token").value();
                                            window.open(url);
                                        });
                                    });
                                </script>
                            </div>
                            <div class="panel">
                                <div class="panel-heading">使用授权令牌-获取用户信息</div>
                                <div class="input-group">
                                    <input id="access_token" type="text" class="form-control" placeholder="access_token">
                                    <span class="input-group-btn"><button id="go" class="btn btn-default" target="_blank">Go!</button></span>
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
                        </td>
                    </tr>
                </#list>
            </table>
        </#if>
    </div>
</div>
</@p.layout>