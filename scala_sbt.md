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
