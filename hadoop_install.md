## Hadoop 2.4.0 Install Guides
#### 环境
+ 主机文件`/etc/hosts`:

		192.168.0.5 master1
		192.168.0.6 master2
		192.168.0.7 data1
		192.168.0.8 data2
+ 生成其他节点文件`subhosts.txt`:

		192.168.0.6
		192.168.0.7
		192.168.0.8

#### 免登陆
+ 规则是master能与所有slave互相访问，slave之间不强制要求访问
+ 创建用户`hadoop`：
		
		useradd hadoop
		passwd hadoop

+ 生成`key`并上传：

		su hadoop
		ssh-keygen –t rsa
		cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
		for f in `cat subhosts.txt`;do 
		for f in `cat subhosts.txt|awk '{print $1}'`;do echo "aaaa" | ssh root@$f 'cat > /tmp/a.txt' ;done 