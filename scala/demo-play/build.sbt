import Build._

name := """demo-play"""

version := $("prod")

scalaVersion := $("scalaVersion")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  //jdbc,
  ehcache,
  ws,
  guice,
  //evolutions,
  "org.apache.shiro" % "shiro-core" % $("shiro"),
  "org.postgresql" % "postgresql" % $("postgresql"),
  "com.h2database" % "h2" % $("h2"),
  "org.springframework.data" % "spring-data-jpa" % $("spring_data_jpa"),
  "org.hibernate" % "hibernate-hikaricp" % $("hibernate"),
  "com.typesafe.play" %% "play-slick" % $("play_slick"),
  "com.typesafe.play" %% "play-slick-evolutions" % $("play_slick"),
  "org.scalatestplus.play" %% "scalatestplus-play" % $("scalatestplus") % Test,
  "junit" % "junit" % $("junit") % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"