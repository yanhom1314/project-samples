import Build._

name := """demo-akka-stream"""

version := "1.1"

scalaVersion := "2.11.8"

lazy val root = project.in(file(".")).enablePlugins(SbtDistApp).settings(
  name := "client",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  //mainClass := Some("sample.stream.TcpEcho"),
  mainClass := Some("sample.command.DemoMain"),
  test in assembly := {},
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-stream" % $("akka"),
    "io.reactivex" %% "rxscala" % $("rxscala"),
    "com.beust" % "jcommander" % $("jcommander"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test"
  ))

fork in run := true
