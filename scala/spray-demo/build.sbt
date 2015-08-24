import Build._

name := """spray-demo"""

version := "1.0"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(SbtTwirl)

mainClass in assembly := Some("demo.Main")

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor" % $("akka"),
							"io.spray" %% "spray-can" % $("spray"),
							"io.spray" %% "spray-routing" % $("spray"),
							"org.scala-lang.modules" %% "scala-xml" % $("scala-xml"),
							"org.scalatest" %% "scalatest" % $("scalatest") % "test")