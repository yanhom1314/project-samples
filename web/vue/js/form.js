import { $, Validator, Vue } from "./lib/util";

//自定义验证
Validator.extend('mobile', {
    messages: {
        zh_CN: field => field + '必须是11位手机号码',
        en: f => f + ' must be a length of 11 digits'
    },
    validate: value => {
        return value.length == 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(value)
    }
});

var app1 = new Vue({
    el: "#app1",
    data: {},
    methods: {
        validateBeforeSubmit() {
            var x = $("form").serializeArray();
            // Validate All returns a promise and provides the validation result.
            this.$validator.validateAll().then(() => {                
                $(".modal-body").html("");
                $.each(x, function (i, field) {
                    $(".modal-body").append(field.name + ":" + field.value + "<br/>");
                });
                // form submit
                $("#myModal").modal("show");
            }).catch(() => {
                if (console) console.log('Correct them errors!');
            });
        }
    }
});

$(function () {
    $("#_close").click(function () {
        console.log("_close");
        $('#myModal').modal("hide");//toggle show hide
    });
    $("#_save").click(function () {
        var action = $("form").attr("action");
        $("#results").append(action + ":" + JSON.stringify($("form").serializeObject()) + "<hr/>");
        $('#myModal').modal('hide');
    });
});