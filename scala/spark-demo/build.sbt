import Build._

name := """spark-demo"""

version := "1.0"

scalaVersion := "2.11.8"

mainClass in assembly := Some("demo.SimpleApp")

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % $("spark"),
	"org.apache.spark" %% "spark-streaming" % $("spark"),
	"org.scalatest" %% "scalatest" % $("scalatest") % "test")