Golang 测试
===
## 安装(Golang)[http://golang.org]
## 演示
+ 创建`$EXT_ROOT_DIR`:

		mkdir -p /test_go/src/hello    //command project dir
		mkdir -p /test_go/src/newmath  //library project dir

+ 设置`GOPATH`环境变量:
						  
		export GOPATH=/phd/bin/go:/test_go

+ 编译安装`pkg`库文件：
		
		go install newmath

+ 编译安装`bin`执行文件：

		go install hello

+ 测试运行：
		
		bin/hello
		>Hello World!
		>Number:%!d(float64=1.7320508075688772)			