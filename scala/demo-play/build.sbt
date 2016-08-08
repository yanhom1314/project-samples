import Build._

name := """demo-play2.5"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,  
  "com.typesafe.play" % "play-slick_2.11" % "2.0.2",
  "io.sinq" %% "sinq-jpa" % $("sinq"),
  "org.springframework.data" % "spring-data-jpa" % $("spring-data-jpa"),
  "org.postgresql" % "postgresql" % "9.4.1209",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)