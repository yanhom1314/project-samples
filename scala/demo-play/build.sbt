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
  "com.typesafe.slick" %% "slick-codegen" % $("slick"),
  "com.typesafe.play" %% "play-slick" % $("play_slick"),
  "com.typesafe.play" %% "play-slick-evolutions" % $("play_slick"),
  "org.scalatestplus.play" %% "scalatestplus-play" % $("scalatestplus") % Test,
  "junit" % "junit" % $("junit") % Test
)

lazy val slickCodeGenTask = TaskKey[Seq[File]]("gen-tables")

slickCodeGenTask := {
  val (dir, cp, r, s) = (sourceDirectory.value, (dependencyClasspath in Compile).value, (runner in Compile).value, streams.value)
  val outputDir = (dir / "../app").getPath
  val url = "jdbc:postgresql://192.168.0.87:5432/testdb"
  val jdbcDriver = "org.postgresql.Driver"
  val profile = "slick.jdbc.PostgresProfile"
  val pkg = "slick.demo"
  val user = "postgres"
  val password = ""
  r.run("slick.codegen.SourceCodeGenerator", cp.files, Array(profile, jdbcDriver, url, outputDir, pkg, user, password), s.log).foreach(sys.error(_))
  val fn = outputDir + "/demo/Tables.scala"
  Seq(file(fn))
}
