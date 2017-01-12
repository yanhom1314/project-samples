##编译
**参考[编译Object C](http://blog.lyxite.com/2008/01/compile-objective-c-programs-using-gcc.html)**


##修改编译include 和 library 配置
+ 编译命令

		gcc -o test test.m -I /GNUstep/System/Library/Headers -L /GNUstep/System/Library/Libraries -lobjc -lgnustep-base
		clang -o test test.m -I /GNUstep/System/Library/Headers -L /GNUstep/System/Library/Libraries -lobjc -lgnustep-base
+ 修改头和库文件路径`vi /etc/profile`

		C_INCLUDE_PATH=/GNUstep/System/Library/Headers
		OBJC_INCLUDE_PATH=/GNUstep/System/Library/Headers
		LIBRARY_PATH=/GNUstep/System/Library/Libraries
		export C_INCLUDE_PATH OBJC_INCLUDE_PATH LIBRARY_PATH

+ 编译

		gcc -o test test.m -lobjc -lgnustep-base
		clang -o test test.m -lobjc -lgnustep-base