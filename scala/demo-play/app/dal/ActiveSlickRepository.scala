package dal

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted.{Rep, TableQuery}

import scala.concurrent.Future

trait ActiveSlickRepository[A <: slick.lifted.AbstractTable[_]] {

  def dbConfigProvider: DatabaseConfigProvider

  def tableQuery: TableQuery[A]

  def $id(a: A): Rep[Long]

  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  def count(): Future[Int] = db.run(tableQuery.size.result)

  def all(): Future[Seq[A#TableElementType]] = db.run(tableQuery.result)

  def single(id: Long): Future[A#TableElementType] = db.run(tableQuery.filter($id(_) === id).result.head.transactionally)

  def filterById(_id: Long) = tableQuery.filter($id(_) === _id)

  def insertOrUpdate(user: A#TableElementType): Future[Int] = db.run(tableQuery.+=(user))
}
