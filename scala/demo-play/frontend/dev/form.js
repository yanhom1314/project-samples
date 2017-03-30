import {$, Validator, Vue} from "./lib/util";

//自定义验证
Validator.extend('mobile', {
    messages: {
        zh_CN: field => field + '必须是11位手机号码',
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
            this.$validator.validateAll().then(success => {
                if (!success) {
                    // handle error
                    return;
                }
                else {
                    // form submit
                    $.each(x, function (i, field) {
                        $("#results").append(field.name + ":" + field.value + "<br/>");
                    });
                    $("#myModal").modal("show");
                }
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
        console.log("_save action:" + action);
        console.log("form data:" + JSON.stringify($("form").serializeObject()));
        $('#myModal').modal('hide');
    });
});