import Build._

lazy val root = project.in(file(".")).aggregate(db, server, client)

lazy val db = project.in(file("db")).settings(
  name := "db",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  exportJars := true,
  libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % $("slick"),
    "com.typesafe.slick" %% "slick-codegen" % $("slick"),
    "org.postgresql" % "postgresql" % $("postgresql"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))

lazy val server = project.in(file("server")).enablePlugins(SbtDistApp).dependsOn(client, db).settings(
  name := "server",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.boot.ServerBoot"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))

lazy val client = project.in(file("client")).settings(
  name := "client",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  exportJars := true,
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))