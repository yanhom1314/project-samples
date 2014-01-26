#### XFS
+ 安装xfs相关包；

		yum install -y xfsprogs kmod-xfs xfsdump xfsprogs-devel
		modprobe xfs
		lsmod |grep xfs

#### 安装
+ 确定各个主机的hostname，并编辑`/etc/hosts`：
	
		###gpb
		172.16.83.1     master1
		172.16.83.2     master2
		172.16.83.3     node1
		172.16.83.4     node2
		172.16.83.5     node3
		172.16.83.6     node4
		172.16.83.7     node5
		172.16.83.8     node6

+ 在所有主机上编辑`/etc/sysctl.conf`

		xfs_mount_options = rw,noatime,inode64,allocsize=16m
		kernel.shmmax = 500000000
		kernel.shmmni = 4096
		kernel.shmall = 4000000000
		kernel.sem = 250 512000 100 2048
		kernel.sysrq = 1
		kernel.core_uses_pid = 1
		kernel.msgmnb = 65536
		kernel.msgmax = 65536
		kernel.msgmni = 2048
		net.ipv4.tcp_syncookies = 1
		net.ipv4.ip_forward = 0
		net.ipv4.conf.default.accept_source_route = 0
		net.ipv4.tcp_tw_recycle = 1
		net.ipv4.tcp_max_syn_backlog = 4096
		net.ipv4.conf.all.arp_filter = 1
		net.ipv4.ip_local_port_range = 1025 65535
		net.core.netdev_max_backlog = 10000
		vm.overcommit_memory = 2		

+ 执行`sysctl -p`

+ 编辑`/etc/security/limits.conf`: 

		* soft nofile 65536
		* hard nofile 65536
		* soft nproc 131072
		* hard nproc 131072
+ 编辑`allhosts.txt`文件：

		master1
		master2
		node1
		node2
		node3
		node4
		node5
		node6
+ 编辑`all_segment_hosts.txt`文件：

		node1
		node2
		node3
		node4
		node5
		node6		

#### Master安装
+ 创建`gpadmin`用户

		useradd gpadmin
		password gpadmin
+ 安装`greenplum-db`，安装路径`/phd/bin/greenplum-db-4.2.6.3B`，安装后会在`/phd/bin`目录下会生成`/phd/bin/greenplum-db`链接目录:

		unzip greenplum-db-4.2.6.3B-build-1-RHEL5-x86_64.zip
		./greenplum-db-4.2.6.3B-build-1-RHEL5-x86_64.bin		
	
+ 创建数库目录`/phd/data`:
		
		mkdir -p /phd/data/master
		chown -R gpadmin /phd/data/master
		chown -R gpadmin /phd/bin/greenplum-db
		chown -R gpadmin /phd/bin/greenplum-db-4.2.6.3B
+ 切换到`gpadmin`用户，编辑`~/.bashrc`文件：
		
		##greenplum
		source /phd/bin/greenplum-db/greenplum_path.sh
+ 切换到`root`用户，生成`ssh key`并分发公钥到所有segment主机：

		source /phd/bin/greenplum-db/greenplum_path.sh	
		gpssh-exkeys -f allhosts.txt 
+ 远程创建`segment`主机的`gpadmin`用户，与修改其密码：

		source /phd/bin/greenplum-db/greenplum_path.sh	
		gpssh -f allhosts.txt -v ntpd
		gpssh -f all_segment_hosts.txt '/usr/sbin/useradd gpadmin -d /home/gpadmin -s /bin/bash'  
		gpssh -f all_segment_hosts.txt 'echo gpadmin | passwd gpadmin --stdin'

#### Segment安装
+ 在`Master`主机上以`root`用户登录，打包已安装`greenplum-db`:

		source /phd/bin/greenplum-db/greenplum_path.sh		
		cd /phd/bin
		gtar -cvf /home/gpadmin/gp.tar greenplum-db-4.2.6.3B
+ 分发安装包到各节点

		gpscp -f all_segment_hosts.txt /home/gpadmin/gp.tar =:/usr/local/

+ 安装segment

		gpssh -f all_segment_hosts.txt 
		=> mkdir -p /phd/bin		
		=> gtar --directory /phd/bin -xvf /usr/local/gp.tar
		=> ln -s /phd/bin/greenplum-db-4.2.6.3B /phd/bin/greenplum-db
		=> chown -R gpadmin:gpadmin /phd/bin/greenplum-db
		=> quit

+ 建立数据库目录

		gpssh -f all_segment_hosts.txt
		=> mkdir -p /phd/data/segment
		=> chown -R gpadmin:gpadmin /phd/data/segment
		=> quit

#### 初始化数据库
+ 登录master主机，复制`gpinitsystem_config`并编辑参数，

		su - gpadmin
		cp /phd/bin/greenplum-db/docs/cli_help/gpconfigs/gpinitsystem_config /home/gpadmin

+ 编辑配置文件`vim gpinitsystem_config`主要参数：

		declare -a DATA_DIRECTORY=(/phd/data/segment /phd/data/segment) #按CPU核数配置
		declare -a MIRROR_DATA_DIRECTORY=(/phd/data/mirror /phd/data/mirror) #按CPU核数配置
		MASTER_HOSTNAME=master1
		MASTER_DIRECTORY=/phd/data/master
		MACHINE_LIST_FILE=all_segment_hosts.txt

+ 初始化数据库

		su - gpadmin
		gpssh-exkeys -f all_segment_hosts.txt		
		gpinitsystem -c /home/gpadmin/gpinitsystem_config
		gpinitsystem -c /home/gpadmin/gpinitsystem_config -s master2 #配置standby master

+ 环境变量`vim ~/.bashrc`:

		MASTER_DATA_DIRECTORY=/phd/data/master
		export MASTER_DATA_DIRECTORY

#### 验证安装

		gpcheck -f /usr/local/greenplum-db/hosts -m gphostm
		gpcheckos -f /usr/local/greenplum-db/hosts
		gpcheckperf -f /usr/local/greenplum-db/hosts -r N -d /tmp
		 
		gpcheckperf -f /usr/local/greenplum-db/hosts-seg -r ds -D -d /gpdata1 -d /gpdata1
		gpcheckperf -h gphost1 -h gphost2 -d /gpdata1 -r d -D -v

#### gpload载入数据
+ 准备数据文件支持txt与csv两种格式
+ 支持gpzip与bzip2压缩文件的自动解压载入；
+ 编辑载入数据配置文件load.yml:
	
		VERSION: 1.0.0.1
		DATABASE: demo
		USER: gpadmin
		HOST: master1
		PORT: 5432
		GPLOAD:
		   INPUT:
		    - SOURCE:
		         LOCAL_HOSTNAME:
		           - master1 
		         PORT: 8081
		         FILE: 
		           - /phd/favdata/*
		    - COLUMNS:
		           - imsi: varchar(50)
		           - userip: cidr
		           - favid: int
		    - FORMAT: TEXT
		    - DELIMITER: ','
		    - ERROR_LIMIT: 25
		    - ERROR_TABLE: err_expenses
		   OUTPUT:
		    - TABLE: favdata
		    - MODE: INSERT
		   SQL:
		    - BEFORE: "INSERT INTO reporttime VALUES('start', current_timestamp)"
		    - AFTER: "INSERT INTO reporttime VALUES('end', current_timestamp)"

+ 在数据存放的机器执行gpload命令：gpload -f load.yml -V -l logs

##### Table
+ 查看表数据分布情况，gp_segment_id是greenplum table里面的一个隐藏列,用来标记该行属于哪个节点：

		select gp_segment_id,count(*) from [tablename] group by gp_segment_id order by count(*) desc;


