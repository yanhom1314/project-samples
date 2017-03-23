import Build._

val common = List(
  organization := "com.example",
  scalaVersion := "2.12.1",
  version := "0.1.0-SNAPSHOT")

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

lazy val root = (project in file(".")).enablePlugins(JavaAppPackaging).
  settings(
    inThisBuild(common),
    name := "hello",
    //mainClass in Compile := Some("demo.oauth2.ExampleServerMain"),
    libraryDependencies ++= Seq(
      "com.twitter" %% "finatra-http" % $("finatra"),
      "org.apache.shiro" % "shiro-core" % $("shiro"),
      "com.github.finagle" %% "finagle-oauth2" % $("finagle-oauth2"),
      "org.jdbi" % "jdbi" % $("jdbi"),
      "org.hibernate" % "hibernate-entitymanager" % $("hibernate"),
      "org.hibernate" % "hibernate-hikaricp" % $("hibernate"),
      "org.threeten" % "threetenbp" % $("threetenbp"),
      "org.springframework.data" % "spring-data-jpa" % $("spring-data-jpa"),
      "org.postgresql" % "postgresql" % $("postgresql"),
      "com.h2database" % "h2" % $("h2"),
      "org.freemarker" % "freemarker" % $("freemarker"),
      "ch.qos.logback" % "logback-classic" % $("logback"),

      "com.twitter" %% "finatra-http" % $("finatra") % "test" classifier "tests",
      "com.twitter" %% "inject-server" % $("finatra") % "test" classifier "tests",
      "com.twitter" %% "inject-app" % $("finatra") % "test" classifier "tests",
      "com.twitter" %% "inject-core" % $("finatra") % "test" classifier "tests",
      "com.twitter" %% "inject-modules" % $("finatra") % "test" classifier "tests",

      "org.mockito" % "mockito-core" % $("mockito") % "test",
      "org.scalatest" %% "scalatest" % $("scalatest") % "test")
  )
  
  
