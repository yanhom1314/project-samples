<#import "layout/main.ftl" as p/>
<#import "/spring.ftl" as s/>
<@p.layout "Access">
<div class="row-fluid">
    <div class="row-fluid">
        <div class="span12 center login-header">
            <h2>第一次授权</h2>
        </div>
    </div>
    <div class="row-fluid">
        <div class="well span5 center login-box">
            <div>
                <form action="<@s.url '/confirm'/>" class="form-horizontal" method="post">
                    <input type="hidden" name="client_id" value="${RequestParameters["client_id"]!}">
                    <input type="hidden" name="response_type" value="${RequestParameters["response_type"]!}">
                    <input type="hidden" name="redirect_uri" value="${RequestParameters["redirect_uri"]!}">
                    <div class="control-group">
                        <div class="controls">
                            <button class="btn" contenteditable="true" type="submit">授权</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</@p.layout>
