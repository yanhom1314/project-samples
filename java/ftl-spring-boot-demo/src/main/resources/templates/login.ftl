<#include "layout/main.ftl"/>
<#import "spring.ftl" as s/>
<#import "tag/security.ftl" as c/>
<@layout "Login">
<div class="row-fluid">
    <div class="row-fluid">
        <div class="span12 center login-header">
            <h2>管理控制台</h2>
        </div>
    </div>
    <div class="row-fluid">
        <div class="well span5 center login-box">
            <#if RequestParameters['error']??>
                <div class="alert alert-danger" role="alert">帐号/密码错误！</div>
            <#elseif RequestParameters['captcha']??>
                <div class="alert alert-danger" role="alert">图形验证码错误！111</div>
            <#else>
                <div class="alert alert-info"> 请输入管理员口令</div>
            </#if>
            <@c.isAuth>
                <h1>已经登录过了！！沙茶</h1>
                <h1><a href="<@s.url '/logout'/>">登出</a></h1>
            </@c.isAuth>
            <@c.isNotAuth>
                <div id="app1">
                    <form @submit.prevent="validateBeforeSubmit" action="<@s.url '/login'/>" method="post" class="form-horizontal">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <fieldset>
                            <div class="input-prepend" title="username" data-rel="tooltip">
                                <span class="add-on"><i class="icon-user"></i></span>
                                <input id="username" name="username" type="text" v-validate="'required'" :class="{'input-large span10': true, 'input-large span10 error': errors.has('username')}"/>
                                <span v-show="errors.has('username')" class="help error" v-cloak>{{ errors.first('username') }}</span>
                            </div>
                            <div class="clearfix"></div>
                            <div class="input-prepend" title="Password" data-rel="tooltip">
                                <span class="add-on"><i class="icon-lock"></i></span>
                                <input id="password" name="password" type="password" v-validate="'required'" :class="{'input-large span10': true, 'input-large span10 error': errors.has('username')}"/>
                                <span v-show="errors.has('password')" class="help error" v-cloak>{{ errors.first('password') }}</span>
                            </div>
                            <div class="clearfix"></div>
                            <div class="input-prepend" title="Password" data-rel="tooltip">
                                <span class="add-on"><i class="icon-lock"></i></span>
                                <input class="input-large span10" name="j_captcha" id="j_captcha" type="text"/>
                            </div>
                            <div>
                                <img id="_captcha" src="${request.contextPath}/captcha" style="width: 120px;height: 25px;" onclick="$('#_captcha').attr('src',this.src);"/>
                            </div>

                            <div class="clearfix"></div>
                            <p class="center span5">
                                <button type="submit" class="btn btn-primary" v-html="submit">Login</button>
                                <button @click="changeLocale" class="btn btn-primary" v-text="nextLocale"></button>
                            </p>
                        </fieldset>
                    </form>
                </div>
            </@c.isNotAuth>
        </div>
    </div>
</div>
<script src="<@s.url '/resources/js/dist/common.min.js'/>"></script>
<script src="<@s.url '/resources/js/dist/form.min.js'/>"></script>
<script>
    window.onload = function () {
        window.g3.init('en');
        console.log("Hello World!");
    }
</script>
</@layout>
