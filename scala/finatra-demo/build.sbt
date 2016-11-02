import Build._

name := """finatra-demo"""

organization := "com.example"

version := "1.0.0"

scalaVersion := "2.11.8"

fork in run := true

javaOptions ++= Seq(
  "-Dlog.service.output=/dev/stderr",
  "-Dlog.access.output=/dev/stderr")

lazy val finatra_demo = (project in file(".")).enablePlugins(SbtDistApp).enablePlugins(JavaAppPackaging).settings(
  mainClass := Some("com.example.ExampleServerMain")
)

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.last
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt" => MergeStrategy.discard
  case "BUILD" => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

test in assembly := {}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % $("finatra"),
  "com.twitter" %% "finatra-httpclient" % $("finatra"),
  "com.twitter" %% "finatra-slf4j" % $("finatra"),
  "com.twitter" %% "finatra-thrift" % $("finatra"),
  "com.twitter" %% "inject-server" % $("finatra"),
  "com.twitter" %% "inject-app" % $("finatra"),
  "org.apache.shiro" % "shiro-core" % $("shiro"),
  "com.github.finagle" %% "finagle-oauth2" % $("finagle-oauth2"),
  "org.jdbi" % "jdbi" % $("jdbi"),
  "org.hibernate" % "hibernate-entitymanager" % $("hibernate"),
  "org.hibernate" % "hibernate-hikaricp" % $("hibernate"),
  "dom4j" % "dom4j" % $("dom4j"),
  "org.springframework.data" % "spring-data-jpa" % $("spring-data-jpa"),
  "org.postgresql" % "postgresql" % $("postgresql"),
  "com.h2database" % "h2" % $("h2"),
  "org.freemarker" % "freemarker" % $("freemarker"),
  "ch.qos.logback" % "logback-classic" % $("logback"),

  "com.twitter" %% "finatra-http" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-server" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-app" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-core" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % $("finatra") % "test" classifier "tests",

  "org.mockito" % "mockito-core" % $("mockito") % "test",
  "org.specs2" %% "specs2" % "2.3.12" % "test",
  "org.scalatest" %% "scalatest" % $("scalatest") % "test")
