Golang 测试
===
## 安装(Golang)[http://golang.org]
## 演示
+ 创建`$EXT_ROOT_DIR`:

		mkdir -p /test_go/src/hello    //command project dir
		mkdir -p /test_go/src/newmath  //library project dir

+ 设置`GOPATH`环境变量:
						  
		export GOPATH=/phd/bin/go:/test_go //window set GOPATH=d:\Tools\go;e:\Github\project-samples\go

+ 编译安装`bin`执行文件：

		go install hello
		go install demo			
		go install modanywhere
		go install speedup   //宽带提速
		go build -ldflags="-s -w" speedup

+ 测试运行：
		
		bin/hello
		>Hello World!
		>Number:%!d(float64=1.7320508075688772)		

		bin/modanywhere
		>curl http://127.0.0.1:8080/	
		
+ 编译安装`pkg`库文件：
        		
        		go install newmath
        		go install mathii


+ 动态库

			go build -x -v -ldflags "-s -w" -buildmode=c-shared -o libnice.so nice				
			gcc -I../ -L../ -lnice -o main main.c