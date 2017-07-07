import Vue from "vue";
import $ from "jQuery";
import jQuery from "jQuery";
import fetch from "fetch";
import axios from "axios";
import cn from "../../node_modules/vee-validate/dist/locale/zh_CN";
import VeeValidate, {Validator} from "vee-validate";

const config = {
    locale: 'zh_CN'
};
Validator.addLocale(cn);

Vue.use(VeeValidate, config);

//export for others scripts to use
// window.$ = $;
// window.jQuery = jQuery;

$.fn.serializeObject = function () {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$.jsonSubmit = function (formElId, successCall, errorCall) {
    var f = $(formElId);
    $.ajax({
        url: f.attr("action"),
        data: JSON.stringify(f.serializeObject()),
        type: f.attr("method") ? f.attr("method") : "get",
        dataType: "json",
        contentType: 'application/json',
        success: function (data) {
            if (!successCall) successCall(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (!errorCall) errorCall(XMLHttpRequest, textStatus, errorThrown)
        }
    });
};
//导出
export {Vue, Validator, fetch, axios, jQuery, $}