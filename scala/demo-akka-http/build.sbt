import Build._

name := """demo-akka-http"""

version := $("prod")

scalaVersion := $("scala")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % $("akka"),
  "com.typesafe.akka" %% "akka-http-spray-json" % $("akka"),
  
  "org.scalatest" %% "scalatest" % $("scalatest") % "test",
  "junit" % "junit" % $("junit") % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")
