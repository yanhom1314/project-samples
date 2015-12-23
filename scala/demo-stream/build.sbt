import scalariform.formatter.preferences._

name := """demo-stream"""

version := "1.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0"
)

scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(AlignSingleLineCaseStatements.MaxArrowIndent, 100)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(PreserveDanglingCloseParenthesis, true)

fork in run := true
