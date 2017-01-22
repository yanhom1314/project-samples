import Vue from "vue";
import $ from "jquery";
import jQuery from "jquery";
import fetch from "fetch";
import VeeValidate,{Validator} from "vee-validate";
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
//导出
export {Vue, Validator, fetch, jQuery, $}