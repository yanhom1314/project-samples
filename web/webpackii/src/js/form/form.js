var $ = require("jquery");
require("jquery-validation");

// jQuery.validator增加IP验证
$.validator.addMethod("ip", function (value, element) {
    return this.optional(element) || (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "Please enter a valid ip address.");

/* 采用不严格的方式验证电话号码。只要电话号码由数字或者数字加"-"(最多两个"-")组 *合构成，均符合。比如：400-823-823， 95555, 010-81567415, 18857107619
 * */
$.validator.addMethod("lenientTel", function (value, element) {
    return this.optional(element) || (/^([0-9]{1,9}(\-)?)?([0-9]{1,9}){1}(\-[0-9]{1,9})?$/.test(value));
}, "电话号码格式错误!");

module.exports = {
    check: function (id) {
        $.validator.addMethod("lenientTel", function (value, element) {
            return this.optional(element) || (/^([0-9]{1,9}(\-)?)?([0-9]{1,9}){1}(\-[0-9]{1,9})?$/.test(value));
        }, "电话号码格式错误!");
        $("#" + id).validate();
    }
};