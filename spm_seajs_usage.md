#### 开发步骤
+ 安装(Nodejs)[http://nodejs.org];
+ 安装`spm`:

        npm install spm -g

+ 基本使用：

        mkdie spm-demo
        cd spm-demo
        spm init


+ 开发编辑`.js`，参考`./index.js`，spm开发的`.js`不需要定义`define(name,[deps],function(require,exports,module){...})`，直接写`...`内容体即可，`spm build`会自动打包封装成`CMD`规范的模块；


+ 打包封装：

        spm build --with-deps               //将开发模块与依赖模块都打包，缺省目录为`dist`；
        spm build --include standlone       //将开发模块与依赖模块打包成一个单独的文件，便于引入；

+ 使用：

        <!DOCTYPE html>
        <html lang="en">
          <head>
            <meta http-equiv="content-type" content="text/html; charset=utf-8" />
            <title>jQuery meets CoffeeScript</title>
            <script src="http://cdn.staticfile.org/seajs/2.3.0/sea.js"></script>

            <script type="text/javascript">
                seajs.config({
                    base: 'http://127.0.0.1/dist',
                    alias: {
                        'index': 'spm-demo/0.0.0/index'
                    }
                });

                seajs.use('index', function(spmDemo) {
                    console.log(spmDemo);
                });
            </script>
          </head>
          <body>
            <h1><span id="message">hello</span></h1>
          </body>
        </html>
