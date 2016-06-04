require("jquery");
require("jquery-validation");

module.exports = {
    check: function (id) {
        $("#"+id).validate();
    }  
};