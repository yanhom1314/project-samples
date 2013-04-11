##Domain Named Server

####查询请求
dig +trace @8.8.8.8 www.baidu.com

####清除缓存
ipconfig /flushdns

####域名规则
+ 如果只有一个DNS，则一般会选取第一条A记录；
+ 如果多个DNS,则一般会选取命中最多的A记录中的第一条；

