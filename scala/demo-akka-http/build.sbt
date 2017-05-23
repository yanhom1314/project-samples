import Build._

name := """demo-akka-http"""

version := $("prod")

scalaVersion := $("scala")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % $("akka-http"),
  "org.json4s" %% "json4s-native" % $("json4s"),
  "com.typesafe.akka" %% "akka-http-spray-json" % $("akka-http"),  
  "org.scalatest" %% "scalatest" % $("scalatest") % "test",
  "junit" % "junit" % $("junit") % "test"
)

//mainClass := Some("demo.WebServer")
mainClass := Some("demo.tcp.DemoBootMain")

enablePlugins(SbtDistApp)

dirSetting ++= Seq("html")


