<#include "../layout/main.ftl"/>
<@layout "Admin">
    <@change_locale/>
<div class="alert alert-info">
    <form id="_person_form" action="<@s.url "/person"/>" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <@s.bind "object.name" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="Name"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <@s.bind "object.age" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="Age"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <@s.bind "object.email" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="Email"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <@s.bind "object.address" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="Address"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <@s.bind "object.password" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="password"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <@s.bind "object.confirmPassword" />
        <input type="text" name="${s.status.expression}" value="${s.status.value!""}" placeholder="Confirm Password"/><br>
        <#list s.status.errorMessages as error> <b>${error}</b> <br> </#list>
        <@s.bind "object.id"/>
        <#if (s.status.value?number >= 0)>
            <input type="hidden" name="${s.status.expression}" value="${s.status.value!""}"/>
            <input type="submit" value="<@s.message "modify"/>"/>
        <#else>
            <input type="submit" value="<@s.message "add"/>"/>
        </#if>
    </form>
    <div class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">用户列表</div>
        <!-- Table -->
        <table class="table">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Age</th>
                <th>Address</th>
            </tr>
            </thead>
            <tbody>
                <#if list??>
                    <#list list as t>
                    <tr>
                        <td>${t.id}</td>
                        <td>${t.name}</td>
                        <td>${t.age}</td>
                        <td>${t.address}</td>
                    </tr>
                    </#list>
                </#if>
            </tbody>
        </table>
    </div>
    <!-- ajax -->
    <div id="table1" class="panel panel-default">
        <!-- Default panel contents -->
        <div class="panel-heading">用户列表</div>
        <!-- Table -->
        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
                <th>Name</th>
                <th>Age</th>
                <th>Address</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="t in items">
                <td><a :href="'<@s.url "/person/show/"/>' + t.id">{{t.id}}</a></td>
                <td>{{t.name}}</td>
                <td>{{t.age}}</td>
                <td>{{t.address}}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <script src="<@s.url '/resources/js/dist/person.min.js'/>"></script>
</@layout>
