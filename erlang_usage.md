## Erlang
+ 变量以大写字母开头；
+ 常量以小写字母开头；
+ 变量值不可变；
+ 模块名与文件名一致；
+ 只有8种基本类型；integer float atom reference fun bitstring 
+ 2种符合类型：元祖：{}	列表：[]



## Rebar构建工具
+ 创建一个项目：

		mkdir app_1
		cd app_1
		rebar create-app appid=app_1
		rebar compile
		cd ..

+ 创建`rebar.config`文件：

		echo {sub_dirs, ["app_1","rel"]}. > rebar.config
		rebar compile

+ 创建一个节点（打包部署）：	

		mkdir rel
		cd rel
		rebar create-node nodeid=myapp1

+ 编辑`rel/reltool.config`文件：
		
		{lib_dirs, [".."]}
		
		{rel, "myapp1", "1",
        [
         kernel,
         stdlib,
         sasl,
         app_1
        ]},
       {rel, "start_clean", "",
        [
         kernel,
         stdlib
        ]},

        {app, app_1, [{mod_cond, app}, {incl_cond, include}]}


+ 打包:
		rebar generate
+ 在`rel`目录下生成`myapp1`目录，将这个目录`zip`后部署即可。
