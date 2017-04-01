# Finatra Typesafe [Activator](https://www.typesafe.com/get-started) Seed Template

[![Build Status](https://secure.travis-ci.org/twitter/finatra-activator-seed.png?branch=master)](http://travis-ci.org/twitter/finatra-activator-seed?branch=master)

A minimal [activator](https://www.typesafe.com/get-started) seed template for creating a Finatra application.

## Spring Data JPA
+ `@Entity`必须用`Java`编写，否则会有很多问题。

## Freemarker & Beetl
+ 所有数据类型需要加`@BeanProperty`，另外属性集合类型必须使用`java.lang.Iterable<T>`类型，否则属性无法解析渲染。
+ 在`Freemarker`模板中所有属性获取必须加`()`，例如：`${person.name()}`。在`Beetl`模板中不需要，例如：`${person.name}`
+ 最好的办法可能是自定义`Freemarker`与`Beetl`中类似`ObjectWrapperMapper`。

## 热加载Reload
+ https://gist.github.com/k33g/9692361

## sbt-assembly & sbt-native-packager
+ add the next content to `build.sbt`

        addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

        addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.2")

+ `assembly` task `MERGE ERROR` add the next content to `build.sbt`:

        assemblyMergeStrategy in assembly := {
            case "BUILD" => MergeStrategy.last
            case x =>
                val oldStrategy = (assemblyMergeStrategy in assembly).value
                oldStrategy(x)
        }

+ `assembly`跳过测试，在`build.sbt`中增加`test in assembly := {}`，也可以在`>set test in assembly := {}`后执行`>assembly`

## 编译运行

+ `sbt assembly`
    
    java -Dfile.encoding=UTF-8 -jar target\scala-2.11\finatra-demo-assembly-x.y.z.jar -admin.port=:8877 -http.port=:80 
    
+ `sbt distZip`

    cd target/universal/stage
    bin/finatra-demo.bat -admin.port=:8877 -http.port=:80
    
__如果需要及时刷新页面请增加参数项：`-local.doc.root=src/main/resources` 其中 `-mustache.templates.dir=templates`是可选的__    

## 乱码问题
+ 统一采用`UTF-8`编码
+ 增加`-Dfile.encoding=UTF-8`参数，也可以`sbt set javaOptions += "-Dfile.encoding=utf-8"`    

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


