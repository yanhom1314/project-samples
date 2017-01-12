#### 开发流程
+ 安装(Nodejs)[http://nodejs.org];
+ 安装`spm`:

        npm install spm -g

+ 基本使用：

        mkdir spm-demo
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


#### jQuery Plugin 封装
+ 以[zTree](http://www.ztree.me)为例：
+ 下载`zTree`并解压到目录`/spm-demo/ztree`
+ 初始化:

        cd /spm-demo/ztree
        spm init --force
        spm install jquery --save

+ 编辑`package.json`中`spm.main`为`js/jquery.ztree.all-3.5.js`
+ 编辑`js/jquery.ztree.all-3.5.js`，采用下面的格式:

        module.exports = function(jQuery) {
            //@start
            //原jquery.ztree.all-3.5.js代码，注意，其他标准的jquery plugin封装的方式一样；
            //@end
        };
+ 部署：
        
        spm publish

+ 构建测试：

        spm build --with-deps

+ 测试页面：

        <!doctype html>
        <html>
        <head>
            <title> ztree demo </title>
            <meta http-equiv="content-type" content="text/html; charset=utf-8">
            <link rel="stylesheet" href="../css/ztreestyle/ztreestyle.css" type="text/css">
            <script src="http://cdn.staticfile.org/seajs/2.3.0/sea.js"></script>
            <script type="text/javascript">
                <!--
                seajs.config({
                    base: 'http://127.0.0.1/dist',
                    alias: {
                        'jquery': 'jquery/2.1.1/jquery',
                        'ztree':'ztree/3.5.16/js/jquery.ztree.all-3.5-debug',
                        'cookie': 'ztree/3.5.16/js/jquery.cookie-debug'
                    }
                });
                var setting = {
                    view: {
                        showLine: false
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };

                var znodes =[
                    { id:1, pId:0, name:"父节点1 - 展开", open:true},
                    { id:11, pId:1, name:"父节点11 - 折叠"},
                    { id:111, pId:11, name:"叶子节点111"},
                    { id:112, pId:11, name:"叶子节点112"},
                    { id:113, pId:11, name:"叶子节点113"},
                    { id:114, pId:11, name:"叶子节点114"},
                    { id:12, pId:1, name:"父节点12 - 折叠"},
                    { id:121, pId:12, name:"叶子节点121"},
                    { id:122, pId:12, name:"叶子节点122"},
                    { id:123, pId:12, name:"叶子节点123"},
                    { id:124, pId:12, name:"叶子节点124"},
                    { id:13, pId:1, name:"父节点13 - 没有子节点", isparent:true},
                    { id:2, pId:0, name:"父节点2 - 折叠"},
                    { id:21, pId:2, name:"父节点21 - 展开", open:true},
                    { id:211, pId:21, name:"叶子节点211"},
                    { id:212, pId:21, name:"叶子节点212"},
                    { id:213, pId:21, name:"叶子节点213"},
                    { id:214, pId:21, name:"叶子节点214"},
                    { id:22, pId:2, name:"父节点22 - 折叠"},
                    { id:221, pId:22, name:"叶子节点221"},
                    { id:222, pId:22, name:"叶子节点222"},
                    { id:223, pId:22, name:"叶子节点223"},
                    { id:224, pId:22, name:"叶子节点224"},
                    { id:23, pId:2, name:"父节点23 - 折叠"},
                    { id:231, pId:23, name:"叶子节点231"},
                    { id:232, pId:23, name:"叶子节点232"},
                    { id:233, pId:23, name:"叶子节点233"},
                    { id:234, pId:23, name:"叶子节点234"},
                    { id:3, pId:0, name:"父节点3 - 没有子节点", isparent:true}
                ];

                seajs.use(['jquery','ztree'],function($,ztree) {
                    ztree($);
                    $.fn.zTree.init($("#treedemo"), setting, znodes);
                });
                //-->
            </script>

        </head>
        <body>
            <ul id="treedemo" class="ztree"></ul>
        </body>
        </html>


#### Spm Build
+ `spm build`会将`.js`文件按模块分别打包输出，如果添加了`--with-deps`参数，会将依赖的模块也打包输出；
+ `spm build --include all`会将当前模块与依赖的模块打包成一个文件输出，产品模式一般采用这种方式；
+ `spm build --include standalone`会将当前模块、依赖模块与Seajs封装成一个单独的`.js`文件，使用的方式：

        <script src="path/to/abc.js"></script> //这种方式没有研究出如果使用`seajs.use([...],function(...){});`
        
