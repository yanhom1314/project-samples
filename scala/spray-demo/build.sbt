name := """spray-demo"""

version := "1.0"

scalaVersion := "2.11.6"

lazy val root = (project in file(".")).enablePlugins(SbtTwirl)

libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "2.2.4" % "test")

fork in run := true