import Build._

name := """yaml-demo"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.fasterxml.jackson.core" % "jackson-databind" % $("jackson"),
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % $("jackson"),
  "org.yaml" % "snakeyaml" % $("snakeyaml"),
  "org.scalatest" %% "scalatest" % $("scalatest") % "test")
