<#import "../layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Client create">
<div class="row-fluid">
    <div class="row-fluid">
        <div class="well span5 center login-box">
            <div>
                <form action="<@s.url '/client/create'/>" class="form-horizontal" method="post">
                    <@s.bind "client.id" />
                    <input type="hidden" name="${s.status.expression}" value="${s.status.value!}"/><#list s.status.errorMessages as e> <b>${e}</b></#list>
                    <@s.bind "client.clientId" />
                    <input type="hidden" name="${s.status.expression}" value="${s.status.value!}"/><#list s.status.errorMessages as e> <b>${e}</b></#list>
                    <@s.bind "client.clientSecret" />
                    <input type="hidden" name="${s.status.expression}" value="${s.status.value!}"/><#list s.status.errorMessages as e> <b>${e}</b></#list>

                    <div class="control-group"><label class="control-label" contenteditable="true" for="_client_name">应用名</label>
                        <div class="controls">
                            <@s.bind "client.clientName" />
                            <input id="_client_name" type="text" name="${s.status.expression}" value="${s.status.value!}"/>
                        </div>
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