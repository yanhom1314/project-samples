import Build._

name := """demo-akka-http"""

version := $("prod")

scalaVersion := $("scala")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % $("akka"),
  "org.json4s" %% "json4s-native" % $("json4s"),
"com.typesafe.akka" %% "akka-http-spray-json" % $("akka"),
  "org.scalatest" %% "scalatest" % $("scalatest") % "test",
  "junit" % "junit" % $("junit") % "test"
)

mainClass := Some("demo.WebServer")

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.last
  case PathList("com", "twitter", xs@_*) => MergeStrategy.last
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt" => MergeStrategy.discard
  case "BUILD" => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

test in assembly := {}

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
