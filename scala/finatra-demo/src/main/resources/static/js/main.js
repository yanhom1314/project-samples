$(function () {

    function success(m) {
        $("#thanks").text(m.message);
        $("#thanks").show(600);
        $("#myModal").modal('hide');
    }

    function error(m) {
        console.log("m:" + JSON.stringify(m));
        var ds = m.errors;
        for (var i in ds) {
            console.log(ds[i]);
        }
        $("#_error").text(ds.join(" "));
        $("#_error").show(600);
    }

    $("button#submit").click(function () {
        //jQuery ajax
        $.ajax({
            type: "POST",
            url: "foo",
            data: $('form.contact').serialize(),
            success: function (m) {
                $("#thanks").text(m.message);
                $("#thanks").show(600);
                $("#myModal").modal('hide');
            },
            error: function (r) {
                var ds = JSON.parse(r.responseText)['errors'];
                console.log()
                for (var i in ds) {
                    console.log(ds[i]);
                }
                $("#_error").text(ds.join(" "));
                $("#_error").show(600);
            }
        });
        //fetch ajax not working
        // fetch('/foo', {method: 'post', body: $('form.contact').serialize()}).then(function (resp) {
        //     if (resp.status !== 200) {
        //         console.log('Looks like there was a problem. Status Code: ' + resp.status);
        //         resp.json().then(function (d) {
        //             console.log(resp.status + " d:" + JSON.stringify(d));
        //             error(d);
        //         });
        //         return;
        //     }
        //     else return resp.json();
        // }).then(function (d) {
        //     console.log("d:" + JSON.stringify(d));
        //     success(d);
        // }).catch(function (err) {
        //     console.log('Fetch Error :-S', err);
        // });
    });
});

