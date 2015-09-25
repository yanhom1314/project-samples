## 性能优化
+ nginx.conf:

worker_processes auto;    		//也可以参考可使用CPU核数配置
worker_rlimit_nofile 100000;	//配置这个就图谱ulimit -a限制

events {
    worker_connections  10240;
    multi_accept on;  
    use epoll; 
}

keepalive_timeout 120;  		//keepalive时间长可以提供channel复用，并减少502错误
tcp_nopush on;  				//tcp参数
tcp_nodelay on;  


+ /etc/sysctl.conf
	
	net.ipv4.tcp_tw_reuse = 1
	net.ipv4.tcp_tw_recycle = 1
	net.ipv4.ip_local_port_range = 10240 65535