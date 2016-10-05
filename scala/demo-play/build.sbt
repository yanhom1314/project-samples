import Build._

name := """demo-play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.postgresql" % "postgresql" % $("postgresql"),
  "org.springframework.data" % "spring-data-jpa" % $("spring-data-jpa"),   
  "org.hibernate" % "hibernate-hikaricp" % $("hibernate"),
  "dom4j" % "dom4j" % $("dom4j"),
  "com.typesafe.play" %% "play-slick" % $("play-slick"),
  //"io.sinq" %% "sinq-jpa" % $("sinq"),
  "org.scalatestplus.play" %% "scalatestplus-play" % $("scalatestplus") % Test
)
