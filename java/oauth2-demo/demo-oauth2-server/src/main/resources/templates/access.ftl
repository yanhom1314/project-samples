<#import "layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Access">
<div class="row-fluid">
    <div class="row-fluid">
        <div class="span12 center login-header">
            <h2>获取授权令牌-后台调用</h2>
        </div>
    </div>
    <div class="row-fluid">
        <div class="well span5 center login-box">
            <div>
                <form action="<@s.url '/accessToken'/>" class="form-horizontal" method="post">
                    <div class="control-group"><label class="control-label" contenteditable="true" for="client_id">应用ID</label>
                        <div class="controls"><input id="client_id" class="input-large span10" name="client_id" type="text" value="${client.clientId!}"/></div>
                    </div>
                    <div class="control-group"><label class="control-label" contenteditable="true" for="client_secret">应用secret</label>
                        <div class="controls"><input id="client_secret" class="input-large span10" name="client_secret" type="text" value="${client.clientSecret!}"/></div>
                    </div>
                    <div class="control-group"><label class="control-label" contenteditable="true" for="grant_type">grant_type</label>
                        <div class="controls"><input id="grant_type" class="input-large span10" name="grant_type" type="text" value="authorization_code"/></div>
                    </div>
                    <div class="control-group"><label class="control-label" contenteditable="true" for="code">授权码</label>
                        <div class="controls"><input id="code" class="input-large span10" name="code" type="text" value="${RequestParameters["code"]!}"/></div>
                    </div>
                    <div class="control-group"><label class="control-label" contenteditable="true" for="redirect_uri">授权码</label>
                        <div class="controls"><input id="redirect_uri" class="input-large span10" name="redirect_uri" type="text" value="http://localhost:8000/index"/></div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button id="j_submit" class="btn btn-primary" contenteditable="true" type="submit">调用</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@p.layout>
