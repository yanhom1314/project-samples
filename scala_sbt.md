## 加速SBT的下载速度
#### 增加国内或者速度快的Maven仓库
+ 无论是`activator`或者是`sbt`，均是修改`sbt.boot.properties`:
+ 在`[repositories]`中增加：
    oschina: http://maven.oschina.net/content/groups/public/
    jcenter: https://jcenter.bintray.com

#### 修改Ivy布局
+ 在`[repositories]`中修改`typesafe-ivy-releasez:`增加：

    typesafe-ivy-releasez: http://maven.oschina.net/content/groups/public/, ...

## 使用代理访问
+ 在`activator`或者`sbt`命令时加入`-Dhttp.proxyHost=[host]`和`-Dhttp.proxyPort=[port]`启动命令

    activator -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=8989 new play-demo play-scala
    sbt -Dhttp.proxyHost=127.0.0.1 -Dhttp.proxyPort=8989 reload
    
## IDE支持

在project/plugins.sbt中添加：

    addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.2.0")      

    addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.4.0")

则在sbt终端可以使用`eclipse`和`gen-idea`命令生成Eclipse和Intelijj IDEA的项目工程文件
> 在`~/.sbt/plugins/plugins.sbt`添加以上内容，则所有sbt项目都默认导入此配置。


## SBT与Play配合
+ 当本地系统中安装了`Play 2.+`后，可以使用`Play`包含的SBT程序。
以Windows为例，编辑sbt.bat脚本：

        @echo off
        set PLAY_HOME=C:/Tools/play-2.1.2-RC1/framework
        set sbt.version=0.12.2        
        set SBT_OPTS=-server -Xmx512m -XX:MaxPermSize=256m -Dsbt.version=%sbt.version% -Dplay.home=%PLAY_HOME% -Dsbt.ivy.home="%PLAY_HOME%/../repository"
        java %SBT_OPTS% -Dsbt.boot.properties="file:///%PLAY_HOME%/sbt/sbt.boot.properties"  -jar "%PLAY_HOME%/sbt/sbt-launch.jar"  %*
        @echo on
+ 修改`%PLAY_HOME%/framework/sbt/sbt.boot.properties`内容，使用Maven Repository和定制ivy-home目录：

        [repositories]
          local
          maven-local
          my-mvn-releases: http://block-1.114dns.com/nexus/content/groups/public/        

然后使用`sbt`命令操作。

## 使用`publish`与`publish-local`
+ 在`build.sbt`或者`project/Build.scala`中定义内容：

        publishTo := Some(Resolver.file("My local maven repo", file("d:/repository")))
则`publish`命令会发布内容到本地Maven Repository，而`publish-local`则会发布到本地ivy repository中。       
