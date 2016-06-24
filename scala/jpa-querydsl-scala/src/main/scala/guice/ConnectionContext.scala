package guice

import java.sql.Connection
import javax.inject.{Inject, Singleton}

import com.zaxxer.hikari.HikariDataSource

@Singleton
class ConnectionContext @Inject()(val dataSource: HikariDataSource) {

  val connHolder = new ThreadLocal[Connection]()

  def getConnection(): Connection = {
    if (connHolder.get() == null) {
      val conn = dataSource.getConnection
      println(s"conn:${conn}")
      connHolder.set(conn)
    }
    connHolder.get()
  }

  def withConnection[T](call: Connection => T): T = {
    var result = null.asInstanceOf[T]
    val conn = getConnection()
    conn.setAutoCommit(true)
    try {
      result = call(conn)
      conn.commit()
    } catch {
      case e: Exception =>
        conn.rollback()
        e.printStackTrace()
    } finally {
      connHolder.remove()
      conn.close()
    }
    result
  }
}
