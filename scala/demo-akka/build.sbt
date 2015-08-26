import org.koala.sbt.SbtAppPlugin._
import Build._

val list = taskKey[Unit]("list")

val filter = (o: Any) => {
  if (o.isInstanceOf[File]) {
    val f = o.asInstanceOf[File]
    if (!f.exists()) sys.error(s">>NOT FOUND ${f.getAbsolutePath}.")
    pattern.matcher(f.name).find() && f.exists()
  }
  else false
}

lazy val root = project.in(file(".")).aggregate(demo_1, demo_2).dependsOn(demo_1, demo_2).settings(
  name := "kernel",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  mainClass := Some("demo.boot.ServerBoot"),
  list <<= (update, dependencyClasspath in Runtime) map {
    (ut, dr) =>
      ut.select(Set("compile")).filter(filter).foreach {
        file =>
          println(s":::${file.name}")
      }
      dr.filter(p => p.data.exists() && p.data.isDirectory).foreach {
        t =>
          t.data.getParentFile.listFiles().filter(filter).headOption match {
            case Some(file) => println(s">>>${file.name}")
            case None => sys.error(s"${t.data.getParent} NOT FOUND JAR FILE.")
          }
      }
  }).settings(appSettings: _*)

lazy val demo_1 = project.in(file("demo_1")).dependsOn(demo_2).settings(
  name := "demo_1",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka"),
    "com.typesafe.akka" %% "akka-remote" % $("akka"),
    "com.typesafe.slick" %% "slick" % $("slick"),
    "com.typesafe.slick" %% "slick-codegen" % $("slick"),
    "org.postgresql" % "postgresql" % $("postgresql"),
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))

lazy val demo_2 = project.in(file("demo_2")).settings(
  name := "demo_2",
  organization := "org.koala",
  version := $("prod"),
  scalaVersion := $("scala"),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-kernel" % $("akka") % "provided",
    "com.typesafe.akka" %% "akka-remote" % $("akka") % "provided",
    "ch.qos.logback" % "logback-classic" % $("logback"),
    "org.scalatest" %% "scalatest" % $("scalatest") % "test",
    "junit" % "junit" % $("junit") % "test"
  ))