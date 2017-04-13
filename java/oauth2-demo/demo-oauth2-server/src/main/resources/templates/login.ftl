<#import "layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Login">
<div class="row-fluid">
    <div class="row-fluid">
        <div class="span12 center login-header">
            <h2>认证平台</h2>
        </div>
    </div>
    <div class="row-fluid">
        <div class="well span5 center login-box">
            <#if RequestParameters["error"]??>
                <div class="alert alert-danger" role="alert">帐号/密码错误！</div>
            <#elseif RequestParameters["captcha"]??>
                <div class="alert alert-danger" role="alert">图形验证码错误！</div>
            <#else>
                <div class="alert alert-info"> 请输入用户名/口令登录！</div>
            </#if>
            <div>
                <form action="<@s.url '/authorize'/>" class="form-horizontal" method="post">
                    <input type="hidden" name="client_id" value="${RequestParameters["client_id"]!}">
                    <input type="hidden" name="response_type" value="${RequestParameters["response_type"]!}">
                    <input type="hidden" name="redirect_uri" value="${RequestParameters["redirect_uri"]!}">
                    <div class="control-group"><label class="control-label" contenteditable="true" for="j_username">帐号</label>
                        <div class="controls"><input id="j_username" class="input-large span10" name="username" type="text" value="test"/></div>
                    </div>
                    <div class="control-group"><label class="control-label" contenteditable="true" for="j_password">密码</label>
                        <div class="controls"><input id="j_password" class="input-large span10" name="password" type="password" value="test"/></div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button id="j_submit" class="btn btn-primary" contenteditable="true" type="submit">登陆</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@p.layout>
