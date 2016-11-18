import Build._

name := """demo-play"""

version := $("prod")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := $("scalaVersion")

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.postgresql" % "postgresql" % $("postgresql"),
  "org.springframework.data" % "spring-data-jpa" % $("spring_data_jpa"),   
  "org.hibernate" % "hibernate-hikaricp" % $("hibernate"),
  //"dom4j" % "dom4j" % $("dom4j"),
  "com.typesafe.play" %% "play-slick" % $("play_slick"),  
  "org.scalatestplus.play" %% "scalatestplus-play" % $("scalatestplus") % Test,
  "junit" % "junit" % "4.12" % Test
)
