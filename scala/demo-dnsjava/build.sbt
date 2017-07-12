import Build._
import sbt.Keys._

lazy val root = project.in(file(".")).enablePlugins(SbtDistApp).settings(
  name := """demo-dnsjava""",
  version := "1.0",
  scalaVersion := "2.12.2",
  mainClass := Some("com.example.Hello"),
  libraryDependencies ++= Seq(
    "dnsjava" % "dnsjava" % $("dnsjava"),
    "com.google.guava" % "guava" % $("guava"),
    "io.undertow" % "undertow-core" % $("undertow"),
    "io.undertow" % "undertow-servlet" % $("undertow"),
    "io.undertow" % "undertow-websockets-jsr" % $("undertow"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test")
)