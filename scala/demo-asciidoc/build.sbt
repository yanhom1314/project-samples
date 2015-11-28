name := """demo-akka-stream"""

version := "1.1"

scalaVersion := "2.11.7"

enablePlugins(SbtDistApp)

libraryDependencies ++= Seq(
  "org.asciidoctor" % "asciidoctorj" % "1.5.2"  
)

mainClass := Some("sample.asciidoc.MainDoc")

fork in run := true
