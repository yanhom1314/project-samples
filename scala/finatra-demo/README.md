# Finatra Typesafe [Activator](https://www.typesafe.com/get-started) Seed Template

[![Build Status](https://secure.travis-ci.org/twitter/finatra-activator-seed.png?branch=master)](http://travis-ci.org/twitter/finatra-activator-seed?branch=master)

A minimal [activator](https://www.typesafe.com/get-started) seed template for creating a Finatra application.


## sbt-assembly & sbt-native-packager
+ add the next content to `build.sbt`

        addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.2")

        addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.0-RC1")

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
    java -jar target\scala-2.11\finatra-demo-assembly-x.y.z.jar -admin.port=:8877 -http.port=:80 -local.doc.root=src/main/resources -mustache.templates.dir=templates


## Mustache Template
+ `Mustache`的缺省从`classpath`获取模版文件，`mustache.templates.dir`的缺省值为`templates`。
+ `local.doc.root`与`doc.root`是互斥的，当设置了`local.doc.root`时设定为从`local file system`获取文件，这个时候`Mustache`是从`local.doc.root`/`mustache.templates.dir`获取文件
+ 新增参数`mustache.templates.suffix`设定模板文件后缀名，缺省为`.mustache`
