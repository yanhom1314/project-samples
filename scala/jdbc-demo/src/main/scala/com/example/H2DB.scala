package com.example

import java.sql.{Connection, DriverManager}

import org.h2.tools.Server

/**
  * Created by YaFengLi on 2016/2/2.
  */
object H2DB {

  lazy val server = Server.createTcpServer()

  def init(): Unit = {
    if (!server.isRunning(true)) {
      server.start()
      Class.forName("org.h2.Driver")
    }

  }

  def shutdown(): Unit = {
    if (server.isRunning(true)) server.stop()
  }

  def withConnection(call: Connection => Unit): Unit = {
    //[jdbc:h2:tcp://localhost/~/test] [jdbc:h2:~/test] [jdbc:h2:mem:test]
    val conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "")
    try {
      conn.setAutoCommit(true)
      call(conn)
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
        conn.rollback()
    }
    finally {
      conn.close()
    }
  }
}
