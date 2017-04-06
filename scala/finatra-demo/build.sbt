import Build._

name := """finatra-demo"""

organization := "com.example"

version := "1.0.0"

scalaVersion := "2.12.1"

fork in run := true

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq("-target:jvm-1.8")

lazy val finatra_demo = (project in file(".")).enablePlugins(SbtDistApp).enablePlugins(SbtTwirl).settings(
  //mainClass in assembly := Some("com.example.ExampleServerMain"),
  //mainClass := Some("demo.IpFindDomain")
  mainClass := Some("com.example.ExampleServerMain")
)

assemblyMergeStrategy in assembly := {
  case x if x.endsWith("io.netty.versions.properties") => MergeStrategy.first
  case PathList("javax", "servlet", _@_*) => MergeStrategy.last
  case PathList(ps@_*) if ps.last endsWith ".class" => MergeStrategy.first
  case PathList(ps@_*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf" => MergeStrategy.first
  case "unwanted.txt" | "changelog.txt" | "BUILD" => MergeStrategy.discard
  case x if x.endsWith("spring.factories") => MergeStrategy.first
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}

test in assembly := {}

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % $("finatra"),
  "org.freemarker" % "freemarker" % $("freemarker"),
  "com.ibeetl" % "beetl" % $("beetl"),
  "org.antlr" % "antlr4-runtime" % $("antlr"),
  "org.apache.shiro" % "shiro-core" % $("shiro"),
  "com.github.finagle" %% "finagle-oauth2" % $("finagle-oauth2"),
  "org.jdbi" % "jdbi" % $("jdbi"),
  //"org.eclipse.persistence" % "org.eclipse.persistence.jpa" % "2.6.4",
  "org.hibernate" % "hibernate-entitymanager" % $("hibernate"),
  "org.hibernate" % "hibernate-hikaricp" % $("hibernate"),
  "org.threeten" % "threetenbp" % $("threetenbp"),
  "org.springframework.data" % "spring-data-jpa" % $("spring-data-jpa"),
  "com.zaxxer" % "HikariCP" % $("HikariCP"),
  "org.postgresql" % "postgresql" % $("postgresql"),
  "com.h2database" % "h2" % $("h2"),
  "ch.qos.logback" % "logback-classic" % $("logback"),

  "com.twitter" %% "finatra-http" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-server" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-app" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-core" % $("finatra") % "test" classifier "tests",
  "com.twitter" %% "inject-modules" % $("finatra") % "test" classifier "tests",

  "org.mockito" % "mockito-core" % $("mockito") % "test",
  "org.scalatest" %% "scalatest" % $("scalatest") % "test")
