name := """yaml-demo"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % "2.7.3",
    "org.yaml" % "snakeyaml" % "1.17",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test")
