PostgreSQL 9 
##Hot standby 实践
1. 主机环境

    	A：192.168.1.1
		B：192.168.1.2
2. 安装步骤

##主机A：
1. 安装PostgreSQL 9.x.x版本

        /configure --prefix=/path
        gmake 
        gmake install
        useradd postgres
        sudo -u postgres /path/bin/initdb /data/path

+ 编辑/data/path目录下配置文件postgresql.conf:`listen_addresses = '192.168.1.1'`

+ 性能调优
        shared_buffers = 2048MB
        work_mem = 256MB 
        maintenance_work_mem = 512MB
        checkpoint_segments = 25
        effective_cache_size = 4096MB
        constraint_exclusion = on
        max_locks_per_transaction = 512 
+ 主备配置 
        wal_level = hot_standby
        max_wal_senders = 1
        wal_keep_segments = 32
        log_destination = 'stderr'
        logging_collector = on
+ 参数listen_addresses是bind的地址列表，必须配置，否则备份机无法通过TCP/IP连接到主服务器
+ pg_hba.conf:
        host    all             all             192.168.1.2/32        trust
+ replication privilege.
        host    replication     postgres        192.168.1.2/32        trust

+ 启动postgres
    sudo -u postgres /path/bin/pg_ctl -D /data/path start -l /home/postgres/pg_log
        sudo -u postgres /path/bin/psql
        postgres#SELECT pg_start_backup('');

+ 停止基础备份
        sudo -u postgres /path/bin/psql
        postgres#SELECT pg_stop_backup();

**注意**：执行SELECT  pg_start_backup后所有请求在写日志之后不会再刷新到磁盘。除非pg_stop_backup()这个函数
被执行。接下来把data目录过scp、rsync等工具同步到节点192.168.1.2服务器，则完成了基础备份的内容。

##主机B：
1. 安装PostgreSQL 9.x.x版本
+ 编辑/data/path目录下配置文件，postgresql.conf:
        hot_standby = on
新增recovery.conf:
        standby_mode = 'on'
        primary_conninfo = 'host=192.168.1.1 port=5432 user=postgres'
        trigger_file = '/phd/db/data/postgresql.trigger.1'
+ 启动postgres

##测试
在主机A上**增删**内容，在主机B上能看到**备份**的结果。

> 性能调优编辑postgresql.conf：

        shared_buffers = 2048MB
        work_mem = 256MB 
        maintenance_work_mem = 512MB
        checkpoint_segments = 25
        effective_cache_size = 4096MB
        constraint_exclusion = on
        max_locks_per_transaction = 512  
##查看表、索引、库大小

####查看库大小
		select pg_size_pretty(pg_database_size('db_name')); 
####查看所有索引大小
* relpages(磁盘使用量)

		SELECT relname, reltuples, relpages FROM pg_class ORDER BY relpages DESC        
* reltuples(记录数)
 
  		SELECT relname, reltuples, relpages FROM pg_class ORDER BY reltuples DESC
* 查看单个表大小

		select pg_size_pretty(pg_relation_size('table_name'))		


