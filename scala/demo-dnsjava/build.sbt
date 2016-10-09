name := """demo-dnsjava"""

version := "1.0"

scalaVersion := "2.11.8"


enablePlugins(SbtDistApp)

mainClass := Some("com.example.Hello")

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "dnsjava" % "dnsjava" % "2.1.7",
  "io.undertow" % "undertow-core" % "1.4.3.Final",
  "io.undertow" % "undertow-servlet" % "1.4.3.Final",
  "io.undertow" % "undertow-websockets-jsr" % "1.4.3.Final",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")

