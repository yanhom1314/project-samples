import {Vue, Validator, $} from "./lib/util";

//自定义验证
const isMobile = {
    messages: {
        en: (field, args) => field + '必须是11位手机号码',
    },
    validate: (value, args) => {
        return value.length == 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(value)
    }
};
Validator.extend('mobile', isMobile);

//或者直接
// Validator.extend('mobile', {
//     messages: {
//         en:field => field + '必须是11位手机号码',
//     },
//     validate: value => {
//         return value.length == 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(value)
//     }
// });

var app1 = new Vue({
    el: "#app1",
    data: {},
    methods: {
        validateBeforeSubmit() {
            var x = $("form").serializeArray();
            $.each(x, function(i, field){
                $("#results").append(field.name + ":" + field.value + "<br/>");
            });
            // Validate All returns a promise and provides the validation result.
            this.$validator.validateAll().then(success => {
                if (!success) {
                    // handle error
                    return;
                }
                else {
                    // form submit
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