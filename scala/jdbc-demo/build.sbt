import Build._

name := """jdbc-demo"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "cglib" % "cglib-nodep" % $("cglib"),
  "com.typesafe.akka" %% "akka-actor" % $("akka"),
  "com.h2database" % "h2" % $("h2"),
  "org.scalatest" %% "scalatest" % $("scalatest") % "test")


