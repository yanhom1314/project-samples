import Build._

name := """hazelcast-demo"""

version := "1.0"

scalaVersion := "2.11.7"

lazy val root = (project in file("."))

libraryDependencies ++= Seq(
  "com.hazelcast" % "hazelcast" % $("hazelcast"),
  "com.hazelcast" % "hazelcast-client" % $("hazelcast"),
  "junit" % "junit" % "4.11",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")
