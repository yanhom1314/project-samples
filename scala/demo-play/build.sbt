import Build._

name := """demo-play"""

version := $("prod")

scalaVersion := $("scalaVersion")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.pac4j" % "play-pac4j" % $("pac4j"),
  "org.pac4j" % "pac4j-http" % $("_pac4j"),
  "org.postgresql" % "postgresql" % $("postgresql"),
  "org.springframework.data" % "spring-data-jpa" % $("spring_data_jpa"),   
  "org.hibernate" % "hibernate-hikaricp" % $("hibernate"),
  "com.typesafe.play" %% "play-slick" % $("play_slick"),
  "org.scalatestplus.play" %% "scalatestplus-play" % $("scalatestplus") % Test,
  "junit" % "junit" % $("junit") % Test
)
