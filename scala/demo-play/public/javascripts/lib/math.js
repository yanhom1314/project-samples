var $ = jQuery = require("jquery");

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

module.exports = {
    add: function (x, y) {
        return x + y;
    },
    jsonSubmit: function (formId, successCall, errorCall) {
        var f = $("#" + formId);
        $.ajax({
            url: f.attr("action"),
            data: JSON.stringify(f.serializeObject()),
            type: "post",
            dataType: "json",
            contentType: 'application/json',
            success: function (data) {
                if (successCall != undefined) successCall(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (errorCall != undefined) errorCall(XMLHttpRequest, textStatus, errorThrown)
            }
        });
    }
};