import Build._

lazy val demo_akka = project.in(file(".")).aggregate(server, client, message)

lazy val server = project.in(file("server")).enablePlugins(SbtDistApp).dependsOn(message).settings(
  name := "server",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.boot.ServerBoot"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  )).settings(dirSetting ++= Seq("conf"))

lazy val client = project.in(file("client")).enablePlugins(SbtDistApp).dependsOn(server).settings(
  name := "client",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.ClientBoot"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % $("akka"),
    "com.typesafe.akka" %% "akka-stream" % $("akka"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  )).settings(dirSetting ++= Seq("conf"))

lazy val message = project.in(file("message")).enablePlugins(SbtDistApp).settings(
  name := "message",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("message.boot.HelloMain"),
  libraryDependencies ++= Seq(
    "com.google.inject" % "guice" % $("guice"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))
