import Build._

name := """demo-play"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  //jdbc,
  //evolutions,
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % $("play-slick"),
  "com.typesafe.play" %% "play-slick-evolutions" % $("play-slick"),
  "org.springframework.data" % "spring-data-jpa" % $("spring-data-jpa"),
  "org.hibernate" % "hibernate-entitymanager" % $("hibernate-entitymanager"),
  "org.dom4j" % "dom4j" % $("dom4j"),
  "org.postgresql" % "postgresql" % "9.4.1208",
  "com.h2database" % "h2" % "1.4.192",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

fork in run := true