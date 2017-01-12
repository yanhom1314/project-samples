## Erlang

#### 语法约定
+ 变量以大写字母开头；
+ 常量(atom)以小写字母开头；
+ 变量值不可变；
+ 模块名与文件名一致；
+ 只有8种基本类型；integer float atom reference fun bitstring
+ 2种符合类型：元祖：{}	列表：[]

#### 8种数据类型
> 基本数据类型
+ integer ——整数被记作一串十进制数字，例如，12,12375和-23427都是整数。整数的算术运算是准确的，没有精度限制2。
+ atom ——原子在程序中用来表示特异值（distinguished value）。原子记为一串连续的字母数字字符，打头的字符要是小写的。如果原子用一对单引号括起来的话，那么它就可以包含任意字符，包括转义字符。
+ float）——浮点数被表示为满足IEEE754[43]规则的64位浮点数。所有±10E308范围内的实数都可以用Erlang浮点数表示。
+ reference）——引用是全局唯一的符号，只用来比较两个引用是否相等。引用可以通过调用Erlang原语make_ref()来创造。
+ binary）——一个二进制数是一个字节序列。二进制数为二进制数据的存贮提供了一种高空间效率的方法。Erlang提供了组合和分解二进制数的原语，也提供了二进制数的高效的输入/输出原语。对二进制数的完整介绍见参考文献[34]。
+ Pid ——Pid是Process Identifier（进程标识符）的缩写，Pid由Erlang的spawn（…）原语创建，Pid是Erlang进程的引用。
+ port——端口用于与外界通信，由内置函数（BIF3）open_port来创建。消息可以通过端口进行收发，但是这些消息必须遵守所谓“端口协议”（port protocol）的规则。
+ fun——匿名函数是函数闭包4，由表达式“fun(…) -> … end.”来创建。

> 两种复合数据类型：
+ tuple:元组 ——元组是一种包含固定个数的Erlang数据的容器。{ D1, D2, …, Dn }表示一个元组，他的参数是D1, D2, …, Dn。这些参数可以是原始数据类型，也可以是复合数据类型。对元组的元素的访问时间是恒定的。
+ list:列表 ——列表是包含可变个数的Erlang数据的容器。[Dh | Dt]表示一个列表它的第1个元素是Dh，余下的元素是一个列表Dt。[]表示空列表。
[D1, D2, .., Dn]是[D1 | [D2 | .. | [Dn | [ ] ] ] ]的简写形式。列表的第1个元素的访问时间是恒定的。列表的第1个元素称为列表的头（head），除第1个元素以外的剩余部分称为列表的尾（tail）。

> 还提供了两种形式的“语法糖衣”（syntactic sugar）：
+ 字符串（string）——字符串记作用双引号引起来的字符系列。这种写法只不过是字符串里的字符的ASCII码组成的整数列表的“语法糖衣”。例如，字符串“cat”只是是列表[97, 99, 116]的速记法。
+ 记录（record）——记录提供了对每个元素都带有标记的元组的一种便利的访问方式。使得我们可以通过名字而不是通过位置来访问元组的元素。一个预编译器会获取到记录的定义，并用正确的元组引用来替换掉记录。


#### 进程操作
+ 创建：使用`spawn(ModuleName,FuntionName,ArgumentList)`函数创建进程，`spawn`接受三个参数:`ModuleName`是模块名，`FuntionName`是函数名，`ArgumentList`是参数列表

		Pid = spawn(Mod,Func,Args)
+ 通信：进程间的通信只能通过消息来完成，消息的发送使用`!`符号

		%% 消息发送
		Pid ！{Message,self()}
		%% 消息接受
		receive
			Message1 [when guard1] ->
				Action1;
			Message1 [when guard1] ->
				Action;
			...
		end
+ 超时：`after TimeOutExpression ->`，其中`TimeOutExpression`一般为整数(毫秒级别)，当为`0`时表示立即执行，`infinity`无穷大表示永不超时；
+ 注册进程：`register(Name,Pid)`将Name与进程Pid联系起来,`unregister(Name)`取消Name与相应进程的对应关系，`whereis(Name)`返回Name所关联的进程的Pid，如果没有进程与之关联，就返回`atom:undefined`，`registered()`返回当前注册的进程的名字列表。




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

*** 注意`app_1`是`app_1/src/app_1.app.src`中定义的`application`相同。 ***

+ 打包:
		rebar generate
+ 在`rel`目录下生成`myapp1`目录，将这个目录`zip`后部署即可。
