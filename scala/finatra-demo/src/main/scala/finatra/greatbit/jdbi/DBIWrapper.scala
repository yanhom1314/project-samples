package finatra.greatbit.jdbi

import org.skife.jdbi.v2.{DBI, Handle}

import scala.reflect.{ClassTag, classTag}

trait DBIWrapper {
  def dbi: DBI

  def withHandle[T](call: Handle => T): T = dbi.withHandle[T]((handle: Handle) => call(handle))

  def withRepo[SqlObjectType: ClassTag, T](call: SqlObjectType => T): T = withHandle { h => call(h.attach(classTag[SqlObjectType].runtimeClass).asInstanceOf[SqlObjectType]) }
}
