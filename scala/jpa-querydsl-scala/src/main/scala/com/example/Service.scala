package com.example

import java.sql.{Connection, DriverManager}

import scalikejdbc.{AutoSession, ConnectionPool, DBSession}

/**
  * Created by YaFengLi on 2016/6/22.
  */
trait Service {

  def call(conn: Connection)(implicit session: DBSession): Unit

  def main(args: Array[String]): Unit = {
    Class.forName("org.h2.Driver")
    val connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "")
    try {
      ConnectionPool.singleton("jdbc:h2:~/test", "sa", "")

      implicit val session = AutoSession

      call(connection)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    } finally {
      connection.close()
      ConnectionPool.closeAll()
    }
  }
}
