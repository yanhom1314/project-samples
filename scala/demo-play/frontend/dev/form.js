import {Vue, Validator} from "./lib/util";

//自定义验证
const isMobile = {
    messages: {
        en: (field, args) => field + '必须是11位手机号码',
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
    el: "#app1",
    data: {},
    methods: {
        validateBeforeSubmit() {
            // Validate All returns a promise and provides the validation result.
            this.$validator.validateAll().then(success => {
                if (!success) {
                    // handle error
                    return;
                }
                else {
                    alert('From Submitted!');
                    // form submit
                }
            });
        }
    }
});