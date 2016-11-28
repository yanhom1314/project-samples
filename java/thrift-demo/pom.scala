import org.sonatype.maven.polyglot.scala.model._
import scala.collection.immutable.Seq

Model(
  "org.apache.shiro.tutorials" % "thrift-tutorial" % "0.0.1-SNAPSHOT",
  name = "First Apache Shiro Application",
  dependencies = Seq(
    "org.apache.thrift" % "libthrift" % "${thrift}",
    "ch.qos.logback" % "logback-classic" % "${logback}",
    "junit" % "junit" "${junit}"
  ),
  properties = Map(
    "thrift" -> "0.9.2",
    "logback" -> "1.1.2",
    "junit" -> "4.12",
    "project.build.sourceEncoding" -> "UTF-8"
  ),
  build = Build(
    plugins = Seq(
      Plugin(
        "org.apache.maven.plugins" % "maven-compiler-plugin" % "3.1",
        configuration = Config(
          source = "1.8",
          target = "1.8",
          encoding = "${project.build.sourceEncoding}"
        )
      ),
      Plugin(
        "org.codehaus.mojo" % "exec-maven-plugin" % "1.2.1",
        executions = Seq(
          Execution(
            goals = Seq(
              "java"
            )
          )
        )
      )
    )
  ),
  modelVersion = "4.0.0"
)
