import Build._

name := """jpa-querydsl-scala"""

version := "1.0"

scalaVersion := $("scala")

libraryDependencies ++= Seq(
  "com.google.inject" % "guice" % $("guice"),
  "com.querydsl" % "querydsl-scala" % $("querydsl"),
  "com.querydsl" % "querydsl-sql-codegen" % $("querydsl"),
  "com.querydsl" % "querydsl-jpa-codegen" % $("querydsl") exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % $("hibernate"),
  "org.scalikejdbc" %% "scalikejdbc" % $("scalikejdbc"),
  "com.h2database" % "h2" % $("h2"),
  "org.scalatest" %% "scalatest" % $("scalatest") % "test")

