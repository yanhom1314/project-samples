import _ from "lodash";
import $ from "jquery";
import jQuery from "jquery";

function component() {
    var element = document.createElement('div');

    element.innerHTML = _.join(['Hello', 'webpack'], ' ');

    return element;
}
$(function () {
    document.body.appendChild(component());
    console.log(jQuery("#accordion"));
});

