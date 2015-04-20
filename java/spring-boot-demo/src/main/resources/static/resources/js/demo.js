var _d6 = (function (m) {
    m.load = function (_url, _csrf, _method, _data) {
        $.ajax({
            type: _method,
            url: _url,
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(_data),
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-CSRF-Token', _csrf);//解决csrf
            },
            success: function (d) {
                console.log(d);
            }
        });
    };

    return m;
})(window._d6 || {});

