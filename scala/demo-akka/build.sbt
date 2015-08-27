val list = taskKey[Unit]("list")

val filter = (o: Any) => {
  if (o.isInstanceOf[File]) {
    val f = o.asInstanceOf[File]
    if (!f.exists()) sys.error(s">>NOT FOUND ${f.getAbsolutePath}.")
    pattern.matcher(f.name).find() && f.exists()
  }
  else false
}

lazy val root = project.in(file(".")).aggregate(db_1, demo_1, demo_2).dependsOn(db_1, demo_1, demo_2).settings(
  name := "kernel",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.boot.ServerBoot"),
  list <<= (update, dependencyClasspath in Compile) map {
    (ut, dr) =>
      ut.select(Set("compile")).filter(filter).foreach {
        file =>
          println(s":[deps]:${file.absolutePath}")
      }
      dr.map(_.data).foreach {
        case f: File if f.isFile && f.name.endsWith(".jar") => println(f">jar:${f.absolutePath}")
        case d: File if d.isDirectory =>
          d.getParentFile.listFiles().filter(filter).headOption match {
            case Some(file) => println(s">[project]>${file.absolutePath}")
            case None => println(s":ERR:${d.absolutePath} NOT FOUND JAR FILE.")
          }
      }
  })

lazy val db_1 = project.in(file("db_1")).settings(
  name := "db_1",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % $("slick"),
    "com.typesafe.slick" %% "slick-codegen" % $("slick"),
    "org.postgresql" % "postgresql" % $("postgresql"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))

lazy val demo_1 = project.in(file("demo_1")).enablePlugins(SbtDistApp).dependsOn(demo_2).settings(
  name := "demo_1",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.boot.ServerBoot"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))

lazy val demo_2 = project.in(file("demo_2")).settings(
  exportJars := true,
  name := "demo_2",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))