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
                <form action="${request.contextPath}/login" method="post" class="form-horizontal">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <fieldset>
                        <div class="input-prepend" title="Username" data-rel="tooltip">
                            <span class="add-on"><i class="icon-user"></i></span>
                            <input class="input-large span10" name="username" id="j_username" type="text" value="admin"/>
                        </div>
                        <div class="clearfix"></div>
                        <div class="input-prepend" title="Password" data-rel="tooltip"><span class="add-on"><i
                                class="icon-lock"></i></span>
                            <input class="input-large span10" name="password" id="j_password" type="password"
                                   value=""/>
                        </div>
                        <div class="clearfix"></div>
                        <div class="input-prepend" title="Password" data-rel="tooltip"><span class="add-on"><i
                                class="icon-lock"></i></span>
                            <input class="input-large span10" name="j_captcha" id="j_captcha" type="text"
                                   value=""/>
                        </div>
                        <div>
                            <img id="_captcha" src="${request.contextPath}/captcha" style="width: 120px;height: 25px;" onclick="$('#_captcha').attr('src',this.src);"/>
                        </div>

                        <div class="clearfix"></div>
                        <p class="center span5">
                            <button type="submit" class="btn btn-primary">登录</button>
                        </p>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</@p.layout>
