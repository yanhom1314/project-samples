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
                            <a href="<@s.url '/authorize?client_id=${t.clientId!}&response_type=code&redirect_uri=http://localhost:8000'/><@s.url '/access/${t.id}'/>" target="_blank">认证授权</a><br/>
                            <div class="panel">
                                <div class="panel-heading">使用授权令牌-获取用户信息</div>
                                <div class="input-group">
                                    <input id="access_token_${t.id}" type="text" class="form-control" name="access_token" placeholder="access_token">
                                    <span class="input-group-btn"><button id="go_${t.id}" class="btn btn-default" target="_blank">Go!</button>
                                </span>
                                </div><!-- /input-group -->
                                <script>
                                    $(function () {
                                        $("#go_${t.id}").click(function () {
                                            var url = "<@s.url '/v1/openapi/me?access_token='/>" + $("#access_token_${t.id}").val();
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