package com.example

import java.sql.Connection

import org.joda.time.DateTime
import scalikejdbc._

/**
  * Created by YaFengLi on 2016/6/21.
  */
object ScalikeJdbcMain extends Service {

  override def call(conn: Connection)(implicit session: DBSession): Unit = {

    val entities: List[Map[String, Any]] = sql"select * from members".map(_.toMap).list.apply()

    // find all Members
    val members: List[Member] = sql"select * from members".map(rs => Member(rs)).list.apply()

    println(s"members:${members}")
    // use paste mode (:paste) on the Scala REPL
    val m = Member.syntax("m")
    val name = "Alice"
    val alice: Option[Member] = withSQL {
      select.from(Member as m).where.eq(m.name, name)
    }.map(rs => Member(rs)).single.apply()
    
    println(s"alice:${alice}")
  }
}

case class Member(id: Long, name: Option[String], createdAt: DateTime)

object Member extends SQLSyntaxSupport[Member] {
  override val tableName = "members"

  def apply(rs: WrappedResultSet): Member = new Member(
    rs.long("id"), rs.stringOpt("name"), rs.jodaDateTime("created_at"))
}