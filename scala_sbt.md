## IDE支持

在project/plugins.sbt中添加：
    
    addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.1.2")      

    addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.1.0")

则在sbt终端可以使用`eclipse`和`gen-idea`命令生成Eclipse和Intelijj IDEA的项目工程文件
> 在~/.sbt/plugins/plugins.sbt添加以上内容，则所有sbt项目都默认导入此配置。


## SBT与Play配合

+ 当本地系统中安装了Play 2.+后，可以使用Play包含的SBT程序。
以Windows为例，编辑sbt.bat脚本：

    @echo off
    set PLAY_HOME=C:\Tools\play-2.1.0
    rem -Dsbt.ivy.home="%PLAY_HOME%/repository"
    set SBT_OPTS=-server -Xmx512m -XX:MaxPermSize=256m
    set SCRIPT_DIR=%PLAY_HOME%/framework/sbt
    java %SBT_OPTS% -Dsbt.boot.properties="file:///%PLAY_HOME%/framework/sbt/sbt.boot.properties"  -jar "%SCRIPT_DIR%/sbt-launch.jar"  %* 
    @echo on
+ 修改%PLAY_HOME%/framework/sbt/sbt.boot.properties内容，使用Maven Repository和定制ivy-home目录：

    [repositories]
      local
      maven-local
      my-mvn-releases: http://221.231.148.247/nexus/content/groups/public/

    [ivy]
      ivy-home: C:/Tools/play-2.1.0/repository

然后使用`sbt`命令操作。

## 使用publish与publish-local
+ 在project/plugins.sbt定义内容：
    
    resolvers += "Local Maven Repository" at "file:///d:/repository/"
则`publish`命令会发布内容到本地Maven Repository，而`publish-local`则会发布到本地ivy repository中。       
