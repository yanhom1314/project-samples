# Finatra Typesafe [Activator](https://www.typesafe.com/get-started) Seed Template

[![Build Status](https://secure.travis-ci.org/twitter/finatra-activator-seed.png?branch=master)](http://travis-ci.org/twitter/finatra-activator-seed?branch=master)

A minimal [activator](https://www.typesafe.com/get-started) seed template for creating a Finatra application.


## sbt-assembly & sbt-native-packager
+ add the next content to `build.sbt`

        addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

        addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.1")

+ `assembly` task `MERGE ERROR` add the next content to `build.sbt`:

        assemblyMergeStrategy in assembly := {
            case "BUILD" => MergeStrategy.last
            case x =>
                val oldStrategy = (assemblyMergeStrategy in assembly).value
                oldStrategy(x)
        }

+ `assembly`跳过测试，在`build.sbt`中增加`test in assembly := {}`，也可以在`>set test in assembly := {}`后执行`>assembly`

## 编译运行

    sbt
    >assembly
    java -Dfile.encoding=UTF-8 -jar target\scala-2.11\finatra-demo-assembly-x.y.z.jar -admin.port=:8877 -http.port=:80 
    >distZip
    cd target/universal/stage
    bin/finatra-demo.bat -admin.port=:8877 -http.port=:80
    
__如果需要及时刷新页面请增加参数项：`-local.doc.root=src/main/resources -mustache.templates.dir=templates`__    

## 乱码问题
+ 统一采用`UTF-8`编码
+ 增加`-Dfile.encoding=UTF-8`参数    

## Mustache Template
+ `Mustache`的缺省从`classpath`获取模版文件，`mustache.templates.dir`的缺省值为`templates`。
+ `local.doc.root`与`doc.root`是互斥的，当设置了`local.doc.root`时设定为从`local file system`获取文件，这个时候`Mustache`是从`local.doc.root`/`mustache.templates.dir`获取文件
+ 新增参数`mustache.templates.suffix`设定模板文件后缀名，缺省为`.mustache`

## Nginx Settings
+ 代理配置:

        #cache begin
        proxy_buffering on;
        proxy_cache_valid any 10m;
        proxy_cache_path /data/cache levels=1:2 keys_zone=my-cache:8m max_size=1000m inactive=600m;
        proxy_temp_path /data/temp;
        proxy_buffer_size 4k;
        proxy_buffers 100 8k;
        #cache end
        
        #upstream
        upstream demo_server {
                ip_hash;
        server 127.0.0.1:8888;
        }

+ `location`配置：
        
        location ^~ /static/ {
            proxy_pass http://demo_server/static/;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Real-PORT $remote_port;
            
            proxy_cache my-cache;
            proxy_cache_valid 200;

            proxy_redirect off;
            proxy_connect_timeout 90;
            proxy_send_timeout 90;
            proxy_read_timeout 90;
            proxy_buffer_size 4k;
            proxy_buffers 4 32k;
            proxy_busy_buffers_size 64k;
            proxy_temp_file_write_size 64k;

            access_log  /phd/logs/nginx/access_log;
        }
        
        location ^~ /demo/ {
            proxy_pass http://demo_server/;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Real-PORT $remote_port;
            proxy_redirect off;
            proxy_connect_timeout 90;
            proxy_send_timeout 90;
            proxy_read_timeout 90;
            access_log  /phd/logs/nginx/access_log;
        }


