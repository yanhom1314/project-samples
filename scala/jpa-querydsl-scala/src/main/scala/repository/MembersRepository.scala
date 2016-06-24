package repository

import javax.inject.{Inject, Singleton}

import com.querydsl.core.types.Predicate
import com.querydsl.sql.Configuration
import demo.querydsl.{Members, QMembers}
import guice.{ConnectionContext, Transactional}

import scala.collection.JavaConversions._

/**
  * Created by YaFengLi on 2016/6/23.
  */
@Singleton
class MembersRepository @Inject()(val configuration: Configuration, val connectionContext: ConnectionContext) extends JDBCRepository {
  val members = QMembers as "m"

  @Transactional
  def findById(id: Int): Members = selectFrom(members).where(members.id.eq(id)).fetchOne()

  @Transactional
  def findAll(expr: Predicate): List[Members] = selectFrom(members).where(expr).fetch().toList

  @Transactional
  def all(): List[Members] = selectFrom(members).fetch().toList
}
