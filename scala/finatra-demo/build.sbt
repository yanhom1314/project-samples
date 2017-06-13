import Build._

name := """finatra-demo"""

organization := "com.example"

version := "1.0.0"

scalaVersion := "2.12.2"

fork in run := true

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

scalacOptions ++= Seq("-target:jvm-1.8")

enablePlugins(SbtDistApp,SbtTwirl)

mainClass := Some("com.example.ExampleServerMain")

libraryDependencies ++= Seq(
  "com.twitter" %% "finatra-http" % $("finatra"),
  "greatbit" %% "finatra-freemarker" % $("finatra"),
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
  "org.scalatest" %% "scalatest" % $("scalatest") % "test")
