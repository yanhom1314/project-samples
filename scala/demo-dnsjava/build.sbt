name := """demo-dnsjava"""

version := "1.0"

scalaVersion := "2.11.8"


enablePlugins(SbtDistApp)

mainClass := Some("com.example.Hello")

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
    "dnsjava" % "dnsjava" % "2.1.7",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test")

