import Vue from "vue";
import cn from "../../node_modules/vee-validate/dist/locale/zh_CN";
import en from "../../node_modules/vee-validate/dist/locale/en";
import VeeValidate,{Validator} from "vee-validate";

//扩展vue-validate
Validator.addLocale(cn);
Validator.addLocale(en);
Vue.use(VeeValidate);

Validator.extend('mobile', {
    messages: {
        zh_CN: field => field + '必须是11位手机号码',
        en: f => f + ' must be a length of 11 digits'
    },
    validate: value => {
        return value.length == 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/.test(value)
    }
});

//export
export {Vue,Validator,VeeValidate}