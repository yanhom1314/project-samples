import {Vue, Validator, VeeValidate} from "./validation";
import $ from "jquery";
import jQuery from "jquery";
import axios from "axios";
//注册空间
window.g3 = window.g3 || {};

//扩展JQuery，对应serializeArray方法，生成json对象
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
//export
export {Vue, Validator, VeeValidate, axios, jQuery, $}