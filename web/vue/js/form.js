import { $, Vue } from "./lib/util";

var app1 = new Vue({
    el: "#app1",
    data: {
        locale: 'zh_CN',       
    },
    computed: {
        nextLocale() {
            return this.locale === 'en' ? '切换为中文' : 'Change Locale To English';
        }
    },
    methods: {
        init:function(){
            //alert("init the current locale and language!");
            this.$validator.setLocale(this.locale);            
        },
        changeLocale() {
            this.locale = this.locale === 'en' ? 'zh_CN' : 'en';           
            this.$validator.setLocale(this.locale);
        },
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
app1.init();

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