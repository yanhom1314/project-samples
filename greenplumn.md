#### Greenplum Database Server安装(Greenplum-Database-4.2-Installation-Guide)

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

+ 安装xfs相关包；

		yum install -y xfsprogs kmod-xfs xfsdump xfsprogs-devel
		modprobe xfs
		lsmod |grep xfs
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

		gpcheck -f /phd/bin/greenplum-db/hosts -m gphostm
		gpcheckos -f /phd/bin/greenplum-db/hosts
		gpcheckperf -f /phd/bin/greenplum-db/hosts -r N -d /tmp
		 
		gpcheckperf -f /phd/bin/greenplum-db/hosts-seg -r ds -D -d /gpdata1 -d /gpdata1
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

##### 常用DBA SQL命令 `>psql [database]`
+ 查看表数据分布情况，gp_segment_id是greenplum table里面的一个隐藏列,用来标记该行属于哪个节点：

		select gp_segment_id,count(*) from [tablename] group by gp_segment_id order by count(*) desc;
+ 查看对象大小(表、索引、数据库等)
	
		select pg_size_pretty(pg_relation_size(’$schema.$table’));
 
+ 查看用户(非系统)表和索引

		select * from pg_stat_user_tables; 
		select * from pg_stat_user_indexes;
+ 查看表分区
		
		select b.nspname||’.'||a.relname as tablename, d.parname as partname
		from pg_class a, pg_namespace b, pg_partition c, pg_partition_rule d
		where a.relnamespace = b.oid
		and b.nspname = ‘$schema’
		and a.relname = ‘$table’
		and a.oid = c.parrelid
		and c.oid = d.paroid
		order by parname;
 
+查看Distributed key

		select  b.attname
		from pg_class a, pg_attribute b, pg_type c, gp_distribution_policy  d, pg_namespace e
		where d.localoid = a.oid
		and a.relnamespace = e.oid
		and e.nspname = ‘$schema’
		and a.relname=’$table’
		and a.oid = b.attrelid
		and b.atttypid = c.oid
		and b.attnum > 0
		and b.attnum = any(d.attrnums)
		order by attnum;
+ 查看当前存活的查询
		
		select   *   from   pg_stat_activity;
 
+ 表上被用作外键的字段名
		
		select f.conname, pg_get_constraintdef(f.oid), t2.relname
		from pg_class t, pg_class t2, pg_constraint f
		where f.confrelid = t.oid
		and f.conrelid = t2.oid
		and f.contype = ‘f’
		and t.relname = ‘$table’;

+ 查看表使用空间
		
		SELECT      *  
		FROM         PUBLIC.dba_segments  
		WHERE         owner   LIKE   'owber_name'
		AND            table_name   LIKE   '%table_name%'
		ORDER   BY   table_name;
 
+ GP中查看分区：
		
		select   partitionname,partitionboundary   from   pg_partitions   where   tablename='table_name';  
		select   partitionname,partitionboundary   from   pg_catalog.pg_partitions   where   tablename='table_name';
 
+ 查看正在运行的sql
		
		select   *   from   pg_stat_activity;
 
+ 修改表的owner语句
		
		Alter table table_name owner to owner_name;
   
+ 增加表分区
		
		ALTER TABLE table_name   ADD PARTITION P20091001  START (DATE '2009-10-01') INCLUSIVE END (DATE '2009-10-02')  EXCLUSIVE WITH(appendonly=true,compresslevel=5);
 
+ 修改列类型
		
		ALTER TABLE table_name  ALTER COLUMN a TYPE varchar(2048);
 
+ 修改distributed列
	
		alter table table_name  set distributed by(column_1);

#### Greenplum Command Center安装(Greenplum Command Center X.Y.Z Administrator Guide:2+Setting Up Greenplum Command Center)
+ `Greenplum Command Center`是`Greenplum Big Data Platform`的管理平台，它将信息存储在`gpperfmon`数据库中；
+ Setting up Greenplum Command Center

		su - gpadmin		
		gpperfmon_install --enable --password p@$$word --port 5432 //启用gpperfmon
		gpstop -r 												   //重启Greenplumn Database Server
		ps -ef | grep gpmmon                                       //确认gpmon进程启动
		psql gpperfmon -c 'SELECT * FROM system_now;'              //确认数据写入数据库
		psql gpperfmon                                             //登录数据库
+ 安装Web

		su - gpadmin
		cd /phd/bin
		chmod +x greenplum-cc-web-versionx.x-PLATFORM.bin
		./greenplum-cc-web-versionx.x-PLATFORM.bin
		>Yes
		source /phd/bin/greenplum-cc-web-versionx.x/gpcc_path.sh
		gpccinstall -f allhosts.txt
+ `vim ~/.bash_profic`增加：

		GPPERFMONHOME=/phd/bin/greenplum-cc-web
		source $GPPERFMONHOME/gpcc_path.sh
+ 重启Database Server：`gpstop`、`gpstart`
+ 运行`gpcmdr --setup`创建instance name
+ 启动`gpcmdr --start`
+ 访问`http://monitor_hostname:28080/`，按安装时定义的密码或者查询`~/.pgpass`文件。


#### 命令
+ `createdb [databasename]`创建数据库
		
