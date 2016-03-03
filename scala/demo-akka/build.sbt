import Build._

scalaVersion := $("scala")

lazy val demo_akka = project.in(file(".")).aggregate(server, client)

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

lazy val server = project.in(file("server")).enablePlugins(SbtDistApp).settings(
  name := "server",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.example.boot.ServerBoot"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))

lazy val client = project.in(file("client")).enablePlugins(SbtDistApp).settings(
  name := "client",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.ClientBoot"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))
