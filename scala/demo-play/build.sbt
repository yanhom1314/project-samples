import Build._

name := """demo-play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % $("play-slick"),
  "io.sinq" %% "sinq-jpa" % $("sinq"),
  "org.springframework.data" % "spring-data-jpa" % $("spring-data-jpa"),
  "org.postgresql" % "postgresql" % $("postgresql"),
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)
