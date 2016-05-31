import Build._

name := """ignite-demo"""

version := "1.0"

scalaVersion := "2.11.8"

lazy val ignite_demo = (project in file(".")).enablePlugins(SbtDistApp).settings(mainClass := Some("demo.LocalGettingStarted"))

libraryDependencies ++= Seq(
  "org.apache.ignite" % "ignite-core" % $("ignite"),
  "org.apache.ignite" % "ignite-spring" % $("ignite"),
  "org.apache.ignite" % "ignite-indexing" % $("ignite"),
  "org.apache.ignite" % "ignite-scalar" % $("ignite"),
  "com.h2database" % "h2" % $("h2"))

