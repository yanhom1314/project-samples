import Build._

name := """spark-demo"""

version := "1.0"

scalaVersion := "2.11.8"

mainClass in assembly := Some("demo.SimpleApp")

test in assembly := {}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)

assemblyMergeStrategy in assembly := {
	case PathList(ps@_*) if ps.last endsWith ".inf" => MergeStrategy.first
	case PathList(ps@_*) if ps.last endsWith ".properties" => MergeStrategy.last
	case PathList("javax", "persistence", xs@_*) => MergeStrategy.last
	case "META-INF/io.netty.versions.properties" => MergeStrategy.discard
	case x =>
		val oldStrategy = (assemblyMergeStrategy in assembly).value
		oldStrategy(x)
}

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % $("spark") % "provided",
	"org.apache.spark" %% "spark-streaming" % $("spark") % "provided",
	"org.scalatest" %% "scalatest" % $("scalatest") % "test")