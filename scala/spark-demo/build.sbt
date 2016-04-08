import Build._

name := """spark-demo"""

version := "1.0"

scalaVersion := "2.10.5"

mainClass in assembly := Some("demo.DSLMain")

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % $("spark"),
							"org.scalatest" %% "scalatest" % $("scalatest") % "test")