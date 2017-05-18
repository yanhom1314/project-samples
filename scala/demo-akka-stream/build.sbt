import Build._

name := """demo-akka-stream"""

version := "1.1"

scalaVersion := $("scala")

lazy val root = project.in(file(".")).enablePlugins(SbtDistApp).settings(
  name := "client",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("sample.command.DemoMain"),  
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-stream" % $("akka"),
    "io.reactivex" %% "rxscala" % $("rxscala"),
    "com.beust" % "jcommander" % $("jcommander"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test"
  ))

fork in run := true
