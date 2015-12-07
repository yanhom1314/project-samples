import scalariform.formatter.preferences._

name := """demo-akka-stream"""

version := "1.1"

scalaVersion := "2.11.7"

enablePlugins(SbtDistApp)

libraryDependencies ++= Seq(
  //"com.typesafe.akka" %% "akka-stream-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-stream-experimental" % "2.0-M2",
  "io.reactivex" %% "rxscala" % "0.25.0"
)

scalariformSettings

mainClass := Some("sample.stream.TcpEcho")

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)

fork in run := true
