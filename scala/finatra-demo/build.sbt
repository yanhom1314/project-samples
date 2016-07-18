import Build._

name := """finatra-demo"""

organization := "com.example"

version := "1.0.0-SNAPSHOT"

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
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.last
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

test in assembly := {}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % $("finatra"),
  "com.twitter" %% "finatra-httpclient" % $("finatra"),
  "com.twitter" %% "finatra-slf4j" % $("finatra"),
  "com.twitter" %% "inject-server" % $("finatra"),
  "com.twitter" %% "inject-app" % $("finatra"),
  "ch.qos.logback" % "logback-classic" % $("logback"),
  "org.mockito" % "mockito-core" % $("mockito") % "test",
  "org.scalatest" %% "scalatest" % $("scalatest") % "test")
