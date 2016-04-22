import scalariform.formatter.preferences._

name := """demo-akka-stream"""

version := "1.1"

scalaVersion := "2.11.7"

enablePlugins(SbtDistApp)

libraryDependencies ++= Seq(
  //"com.typesafe.akka" %% "akka-stream" % "2.4,4",
  "com.typesafe.akka" % "akka-stream_2.11" % "2.4.4",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4",
  "io.reactivex" %% "rxscala" % "0.26.1"
)

scalariformSettings

mainClass := Some("sample.stream.TcpEcho")

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)

fork in run := true
