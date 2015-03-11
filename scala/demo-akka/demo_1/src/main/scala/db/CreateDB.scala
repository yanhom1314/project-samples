package db

import scala.slick.driver.PostgresDriver.simple._

object CreateDB extends App {

  implicit val db = Database.forURL("jdbc:postgresql://127.0.0.1:5432/sure_http_db", driver = "org.postgresql.Driver", user = "postgres")

  database

  private def database(implicit db: Database) = {
    println("###########Slick#########")
    db.withSession {
      implicit session =>
        println(session)
        val q = for {t <- Tables.SUser} yield t.policypassword

        q.list.foreach(t => println(t.get))
    }

    scala.slick.codegen.SourceCodeGenerator.main(
      Array("scala.slick.driver.PostgresDriver", "org.postgresql.Driver", "jdbc:postgresql://127.0.0.1:5432/sure_http_db", "demo_1/src/main/scala", "db", "postgres", "")
    )
    println("###########Slick#########")
  }
}
