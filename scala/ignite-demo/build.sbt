import Build._

name := """ignite-demo"""

version := "1.0"

scalaVersion := "2.11.7"

lazy val ignite_demo = (project in file("."))

libraryDependencies ++= Seq(
  "org.apache.ignite" % "ignite-core" % $("ignite"),
  "org.apache.ignite" % "ignite-spring" % $("ignite"),
  "org.apache.ignite" % "ignite-indexing" % $("ignite"))
