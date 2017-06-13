<#include "../layout/main.ftl"/>
<@layout "Admin">
<div class="alert alert-info">
    <@e.username/>
    <@e.security/>
    <@e.hasRole "ROLE_ADMIN">
        <h1>ROLE_ADMIN!!!</h1>
    </@e.hasRole>
    <@e.notHasRole "ROLE_ADMIN">
        <h1>NOT HAS ROLE_ADMIN!!!</h1>
    </@e.notHasRole>
    <@e.hasRole "ROLE_USER">
        <h1>ROLE_USER!!!</h1>
    </@e.hasRole>
    <@e.notHasRole "ROLE_USER">
        <h1>NOT HAS ROLE_USER!!!</h1>
    </@e.notHasRole>
    <@e.notHasRole "ROLE_OTHER">
        <h1>NOT HAS ROLE_OTHER!!!</h1>
    </@e.notHasRole>
    <h1><@s.message "logout"/></h1>
</div>


<div id="web_socket">
    <form>
        <input id="name" name="name" type="text" value="测试用户"/>
        <input id="age" name="age" type="text" value="59"/>
    </form>
    <button id="_send_btn" type="button" class="btn btn-primary">Hello WebSocket</button>
    <script>
        $(function () {
            var url = 'ws://' + window.location.host + '/ws/count';
            var ws = new WebSocket(url);
            console.log("ws:" + ws);
            ws.onopen = function (e) {
                // ws.send('hello');
            };

            ws.onmessage = function (e) {
                alert(e.data);
            };
            ws.onerror = function (e) {
                alert(e);
            };
            $("#_send_btn").click(function () {
                var formData = $('form').serializeArray();
                console.log("data:" + formData);
                ws.send(JSON.stringify(formData));
            });
        });
    </script>
</div>
</@layout>
