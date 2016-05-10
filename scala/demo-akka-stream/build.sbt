import Build._

name := """demo-akka-stream"""

version := "1.1"

scalaVersion := "2.11.8"

lazy val root = project.in(file(".")).enablePlugins(SbtDistApp).settings(
  name := "client",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("sample.stream.TcpEcho"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-stream" % $("akka"),
    "io.reactivex" %% "rxscala" % $("rxscala"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test"
  ))

fork in run := true
