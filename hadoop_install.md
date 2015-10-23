## Hadoop 2.6.x/2.7.x Install Guides
#### 操作系统
+ Centos 7.1
## 通用步骤
+ 主机规划文件`/etc/hdfs.hosts`	

		192.168.0.6 namenode
		192.168.0.7 datanode1
		192.168.0.8 datanode2
+ 配置主机文件`/etc/hosts`:

		cat /etc/hdfs.hosts >> /etc/hosts
+ 安装软件
		
		yum install java-[verxion].x86_64
		mkdir /usr/local/hadoop 
		cd /usr/local/hadoop
		tar zxf hadoop-2.7.1.tar.gz 
+ 创建用户`hadoop`：
		
		useradd hadoop
		passwd hadoop		

+ 配置免密码登录
+ datanode:

		su hadoop
		ssh-keygen –t rsa
		scp ~/.ssh/id_rsa.pub hadoop@namenode:/home/hadoop/.ssh/id_rsa.pub.[hostname]		

+ namenode:

		su hadoop
		ssh-keygen –t rsa
		cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys		
		cat ~/.ssh/id_rsa.pub.* >> ~/.ssh/authorized_keys
		for f in `cat /etc/hdfs.hosts | awk '{print $1}'`;do scp ~/.ssh/authorized_keys hadoop@$f:/home/hadoop/.ssh/ ;done

+ 从namenode测试`ssh datanode1 && ssh datanode2`
+ 从datanode1测试`ssh namenode && ssh datanode2`
+ 从datanode2测试`ssh namenode && ssh datanode1`

+ 配置`etc/hadoop/core-site.xml`

		<property> 
			<name>fs.defaultFS</name> 
			<value>hdfs://namenode:9000</value> 
		</property> 
		<property> 
			<name>hadoop.tmp.dir</name> 
			<value>/home/hadoop/hd_space/tmp</value> 
		</property>
+ 配置`etc/hadoop/hdfs-site.xml`
		
		<property> 
			<name>dfs.name.dir</name> 
			<value>/home/hadoop/hd_space/hdfs/name</value> 
		</property> 
		<property> 
      		<name>dfs.data.dir</name> 
        	<value>/home/hadoop/hd_space/hdfs/data</value> 
		</property> 
		<property> 
           	<name>dfs.replication</name>   
			<value>2</value> 
		</property> 
		<property> 
         	<name>dfs.namenode.secondary.http-address</name>   
			<value>datanode1:50090</value> 
		</property> 
		<property> 
          	<name>dfs.namenode.secondary.https-address</name>   
			<value>datanode1:50091</value> 
		</property> 
 + 配置`etc/hadoop/yarn-site.xml`

 		<property> 
      		<description>The hostname of the RM.</description>     
			<name>yarn.resourcemanager.hostname</name>     
			<value>namenode</value>   
		</property>   
		<property> 
        	<description>the valid service name should only contain a-zA-Z0-9_ and can not start with numbers</description> 
           	<name>yarn.nodemanager.aux-services</name>    
			<value>mapreduce_shuffle</value>   
		</property>
 + 配置`etc/hadoop/mapred-site.xml`

 		<property> 
			<name>mapreduce.cluster.local.dir</name> 
			<value>/home/hadoop/hd_space/mapred/local</value> 
		</property> 
		<property> 
			<name>mapreduce.cluster.system.dir</name> 
			<value>/home/hadoop/hd_space/mapred/system</value> 
		</property> 
		<property> 
			<name>mapreduce.framework.name</name> 
			<value>yarn</value> 
		</property> 
		<property> 
			<name>mapreduce.jobhistory.address</name> 
			<value>namenode:10020</value> 
		</property> 
		<property> 
			<name>mapreduce.jobhistory.webapp.address</name> 
			<value>namenode:19888</value> 
		</property>
+ 修改`slaves`

		datanode1 datanode2

[Hadoop Cluster Setup](http://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/ClusterSetup.html)

#### 运行Hadoop
+ 使用hadoop用户登录namenode 
+ 首次运行需要格式化hdfs 
		
		hdfs namenode -format    
		//启动dfs 
		start-dfs.sh  
		//停止命令为 
		stop-dfs.sh 
		//启动MapReduce	 
		start-yarn.sh  
		//停止命令为 
		stop-yarn.sh