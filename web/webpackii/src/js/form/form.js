var $ = require("jquery");
require("jquery-validation");

// jQuery.validator增加IP验证
$.validator.addMethod("c_ip", function (value, element) {
    return this.optional(element) || (/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
}, "请输入正确IP地址!");

/* 采用不严格的方式验证电话号码。只要电话号码由数字或者数字加"-"(最多两个"-")组 *合构成，均符合。比如：400-823-823， 95555, 010-81567415, 18857107619
 * */
$.validator.addMethod("c_phone", function (value, element) {
    return this.optional(element) || (/^([0-9]{1,9}(\-)?)?([0-9]{1,9}){1}(\-[0-9]{1,9})?$/.test(value));
}, "电话号码格式错误!");

$.validator.addClassRules("ip",{required:true, c_ip:true});
$.validator.addClassRules("phone",{required:true, c_phone:true});


// alias required to cRequired with new message
 $.validator.addMethod("cRequired", $.validator.methods.required,
   "Customer name required");
 // alias minlength, too
 $.validator.addMethod("cMinlength", $.validator.methods.minlength,
   // leverage parameter replacement for minlength, {0} gets replaced with 2
   $.validator.format("Customer name must have at least {0} characters"));
 // combine them both, including the parameter for minlength
 $.validator.addClassRules("customer", { cRequired: true, cMinlength: 2 });

module.exports = {
    check: function (id) {
        $("#" + id).validate();
    }
};