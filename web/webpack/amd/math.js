define(['jquery'],function($){
    console.log("math:"+$);
    var add = function(x,y) {
        return x + y;
    };
    var inc = function(x) {
        return x + 1;
    };
    return {
        add:add,
        inc:inc
    };
});
