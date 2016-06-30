enablePlugins(ScalaJSPlugin)

name := """scalajs-demo"""

scalaVersion := "2.11.8"

fork in run := true

scalaJSUseRhino in Global := false