## 安装

        npm install webpack -g
        npm install style-loader
        npm install css-loader
        npm install file-loader

## 结构
+ entry.js:

        require("./style.css");

        var content = require("./content.js");
        document.write(content.msg);


+ content.js:

        module.exports = {
            msg:"It works from content.js.",
            sayHello:function(){
                alert("Hello!");
            }
        };
