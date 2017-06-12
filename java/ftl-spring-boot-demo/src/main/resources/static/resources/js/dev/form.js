import { $, Vue } from "./lib/util";

var app1 = new Vue({
    el: "#app1",
    data: {
        locale: 'zh_CN',
        submit: '登录'
    },
    computed: {
        nextLocale() {
            return this.locale === 'en' ? '切换为中文' : 'Change Locale To English';
        }
    },
    methods: {
        init(_locale) {
            console.log("init the current locale and language："+this.locale+".");
            if (_locale != undefined) {
                this.locale = _locale;
                this.submit = this.locale === 'en' ? 'Login' : '登录';
            }
            this.$validator.setLocale(this.locale);
        },
        changeLocale() {
            this.locale = this.locale === 'en' ? 'zh_CN' : 'en';
            this.submit = this.locale === 'en' ? 'Login' : '登录';

            this.$validator.setLocale(this.locale);
        },
        validateBeforeSubmit() {
            var x = $("form").serializeArray();
            // Validate All returns a promise and provides the validation result.
            this.$validator.validateAll().then(() => {
                console.log(111);
                $("form").submit();
            }).catch(() => {
                if (console) console.log('form invalid.');
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
//can call this method in html page.
window.g3.init = (_locale) => { app1.init(_locale); }
