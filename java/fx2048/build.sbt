import Build._

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

lazy val root = (project in file(".")).settings(
  name := """fx2048""",
  version := "1.0",
  scalaVersion := "2.12.1",
  mainClass in assembly :=  Some("sdimkov.game2048.MainApp"),// Some("game2048.Game2048"),
  libraryDependencies ++= Seq(
    "com.google.inject" % "guice" % $("guice"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "junit" % "junit" % $("junit") % "test"
  )
)

