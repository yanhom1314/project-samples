#### 编译要求
+ 需要libpcap支持：`yum install -y libpcap-devel`
+ 安装[Cmake](http://www.cmake.org)


#### 编译

		cd /source/path
		mkdir build && cd build
		cmake .. && make
#### 测试
	
		bin/test

* 代码中提供过滤出合法http包，自行添加需要内容。


#### 测试环境
+ 压力测试[apache ab](http://httpd.apache.org/) 或者[wrk](https://github.com/wg/wrk)均可以，我用的是wrk；
+ 网络拓扑很简单，两台主机A,B：程序安装在A上，A上提供http server，然后从B向A发请求打压力；
+ 两台机器均为dell r720;

