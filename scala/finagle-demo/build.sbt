import Build._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.11.8"
    )),
    name := "finagle-demo",
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-http" % $("finagle"),
      "org.scalatest" %% "scalatest" % $("scalatest") % "test",
      "junit" % "junit" % "4.12" % "test"
    )
  )
