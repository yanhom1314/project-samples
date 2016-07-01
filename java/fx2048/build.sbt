import Build._

name := """fx2048"""

version := "1.0"

scalaVersion := "2.11.8"

mainClass in assembly := Some("game2048.Game2048")

libraryDependencies ++= Seq(
  "junit" % "junit" % $("junit") % "test"
)
