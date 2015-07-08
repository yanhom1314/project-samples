var math = require('math');

var inc = function(x) {
    return math.add(x,1);
};

module.exports = {
    inc:inc
};
