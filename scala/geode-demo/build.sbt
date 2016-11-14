import Build._

name := """geode-demo"""

version := "1.0"

scalaVersion := "2.12.0"

lazy val root = (project in file("."))

lazy val demo_data = (project in file("data_java")).settings(
  libraryDependencies ++= Seq(
    "org.apache.geode" % "geode-core" % $("geode"),
    "org.apache.geode" % "geode-json" % $("geode"))
  )

lazy val geode_demo = (project in file("proc_scala")).dependsOn(demo_data).settings(
  libraryDependencies ++= Seq(
    "org.apache.geode" % "geode-core" % $("geode"),
    "org.apache.geode" % "geode-json" % $("geode"))
  )

libraryDependencies ++= Seq(
  "org.apache.geode" % "geode-core" % $("geode"),
  "org.apache.geode" % "geode-json" % $("geode"))