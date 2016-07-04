import Build._

name := """fx2048"""

version := "1.0"

scalaVersion := "2.11.8"

mainClass in assembly := Some("g2048.Game2048II")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

libraryDependencies ++= Seq(
  "junit" % "junit" % $("junit") % "test"
)
