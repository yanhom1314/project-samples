package guice

import java.sql.Connection
import javax.inject.{Inject, Singleton}
import javax.sql.DataSource

@Singleton
class ConnectionContext @Inject()(val dataSource: DataSource) {

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
      conn.close()
    }
    result
  }
}
