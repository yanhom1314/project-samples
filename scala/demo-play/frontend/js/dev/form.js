import Vue from "vue";
import VeeValidate, {Validator} from "vee-validate";
import messagesCN from "vee-locale-cn";

const config = {
    errorBagName: 'errors', // change if property conflicts.
    fieldsBagName: 'fields',
    delay: 0,
    locale: 'zh_CN',
    dictionary: {
        zh_CN: {
            messages: messagesCN
        }
    }
};
Vue.use(VeeValidate, config);

//自定义验证
const isMobile = {
    messages: {
        en:(field, args) => field + '必须是11位手机号码',
    },
    validate: (value, args) => {
        return value.length == 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(value)
    }
};
Validator.extend('mobile', isMobile);

//或者直接
// Validator.extend('mobile', {
//     messages: {
//         en:field => field + '必须是11位手机号码',
//     },
//     validate: value => {
//         return value.length == 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(value)
//     }
// });

var app1 = new Vue({
    el: "#app1"
});