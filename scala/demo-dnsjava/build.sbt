import Build._
import sbt.Keys._

lazy val root = project.in(file(".")).enablePlugins(SbtDistApp).settings(
  name := """demo-dnsjava""",
  version := "1.0",
  scalaVersion := "2.11.8",
  mainClass := Some("com.example.Hello"),
  libraryDependencies ++= Seq(
    "dnsjava" % "dnsjava" % $("dnsjava"),
    "io.undertow" % "undertow-core" % $("undertow"),
    "io.undertow" % "undertow-servlet" % $("undertow"),
    "io.undertow" % "undertow-websockets-jsr" % $("undertow"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test")
)