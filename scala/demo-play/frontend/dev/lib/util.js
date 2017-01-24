import Vue from "vue";
import $ from "jquery";
import jQuery from "jquery";
import fetch from "fetch";
import VeeValidate, {Validator} from "vee-validate";
import messagesCN from "vee-locale-cn";

const config = {
    errorBagName: 'errors', // change if property conflicts.
    fieldsBagName: 'fields',
    delay: 0,
    locale: 'cn',
    dictionary: {
        cn: {
            messages: messagesCN
        }
    }
};
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
export {Vue, Validator, fetch, jQuery, $}