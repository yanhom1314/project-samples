name := """fx2048"""

version := "1.0"

scalaVersion := "2.11.8"

mainClass in assembly := Some("game2048.Game2048")

libraryDependencies ++= Seq(
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.11" % "test"
)
