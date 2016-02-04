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
            <div class="alert alert-info"> 请输入管理员口令</div>
            <h3 th:text="(${param.containsKey('error')} ? '请核查帐号/密码!':'')" style="color: red"></h3>

            <h3 th:text="(${param.containsKey('captcha')} ? '验证码错误!':'')" style="color: red"></h3>

            <div>
                <form action="#" th:action="@{/login}" method="post" class="form-horizontal">
                    <fieldset>
                        <div class="input-prepend" title="Username" data-rel="tooltip">
                            <span class="add-on"><i class="icon-user"></i></span>
                            <input class="input-large span10" name="username" id="j_username" type="text"
                                   value="admin"/>
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
                            <img id="_captcha" th:src="@{/captcha}" style="width: 120px;height: 25px;"/><a id="_refresh" href="javascript:void(0);">点击刷新</a>
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
</@p.boot>
