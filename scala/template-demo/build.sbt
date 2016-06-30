import Build._

name := """template-demo"""

version := "1.0"

scalaVersion := "2.11.8"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
    "com.github.spullara.mustache.java" % "compiler" % $("mustache.java"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test")

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

