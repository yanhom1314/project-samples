package db

import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

object DemoDB extends App {
  implicit val db = Database.forURL(DBContants.url, driver = DBContants.driver, user = "postgres")
  /**
   * First CREATE Tables Object.
   */
  val q = for {t <- Tables.SAdmin} yield (t.loginpassword, t.loginname)
  db.stream(q.result).foreach { t =>
    println(s"un${t._2} pwd:${t._1}")
  }
}
