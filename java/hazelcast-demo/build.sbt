name := """hazelcast-demo"""

version := "1.0"

scalaVersion := "2.11.7"

lazy val root = (project in file("."))

libraryDependencies ++= Seq(
  "com.hazelcast" % "hazelcast" % "3.5.2",
  "com.hazelcast" % "hazelcast-client" % "3.5.2",
  "junit" % "junit" % "4.11",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")