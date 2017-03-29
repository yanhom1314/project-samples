package finatra.greatbit.jdbi

import java.io.Closeable

import org.skife.jdbi.v2.{DBI, Handle}

import scala.reflect.{ClassTag, classTag}

trait DBIWrapper {
  def dbi: DBI

  def withHandle[T](call: Handle => T): T = withCloseable(dbi.open()) { handle => call(handle) }

  def withRepo[SqlObjectType: ClassTag, T](call: SqlObjectType => T): T = withHandle { h => call(h.attach(classTag[SqlObjectType].runtimeClass).asInstanceOf[SqlObjectType]) }

  def withCloseable[A <: Closeable, T](closeable: A)(call: A => T): T = {
    try {
      call(closeable)
    } finally {
      closeable.close()
    }
  }
}
