<#import "layout/main.ftl" as p/>
<@p.layout "Login">
<div class="row-fluid">
    <div class="row-fluid">
        <div class="span12 center login-header">
            <h2>管理控制台</h2>
        </div>
    </div>
    <div class="row-fluid">
        <div class="well span5 center login-box">
            <#if RequestParameters["error"]??>
                <div class="alert alert-danger" role="alert">帐号/密码错误！</div>
            <#elseif RequestParameters["captcha"]??>
                <div class="alert alert-danger" role="alert">图形验证码错误！</div>
            <#else>
                <div class="alert alert-info"> 请输入管理员口令</div>
            </#if>
            <div>
                <form action="/authorize" class="form-horizontal" method="post">
                    <div class="control-group"><label class="control-label" contenteditable="true" for="j_username">帐号</label>
                        <div class="controls"><input id="j_username" class="input-large span10" name="username" type="text" value="admin"/></div>
                    </div>
                    <div class="control-group"><label class="control-label" contenteditable="true" for="j_password">密码</label>
                        <div class="controls"><input id="j_password" class="input-large span10" name="password" type="password" value=""/></div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label class="checkbox" contenteditable="true"><input type="checkbox"/>记住我</label>
                            <button id="j_submit" class="btn" contenteditable="true" type="submit">登陆</button>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
</@p.layout>
