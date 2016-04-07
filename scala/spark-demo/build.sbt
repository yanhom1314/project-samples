import Build._

name := """spark-demo"""

version := "1.0"

scalaVersion := "2.11.7"

mainClass in assembly := Some("demo.DSLMain")

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % $("spark"),
							"org.scalatest" %% "scalatest" % $("scalatest") % "test")