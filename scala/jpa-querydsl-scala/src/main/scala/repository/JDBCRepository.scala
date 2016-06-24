package repository

import java.sql.Connection

import com.querydsl.core.types.Expression
import com.querydsl.sql.dml.{SQLDeleteClause, SQLInsertClause, SQLUpdateClause}
import com.querydsl.sql.{Configuration, RelationalPath, SQLQuery}
import guice.ConnectionContext

/**
  * Created by YaFengLi on 2016/6/23.
  */
trait JDBCRepository {

  val configuration: Configuration
  val connectionContext: ConnectionContext

  protected def getConnection(): Connection = connectionContext.dataSource.getConnection

  protected def selectFrom[T](entity: RelationalPath[T]): SQLQuery[T] = select(entity).from(entity)

  protected def select[T](entity: Expression[T]): SQLQuery[T] = new SQLQuery(getConnection(), configuration).select(entity)

  protected def insert(path: RelationalPath[_]): SQLInsertClause = new SQLInsertClause(getConnection(), configuration, path)

  protected def update(path: RelationalPath[_]): SQLUpdateClause = new SQLUpdateClause(getConnection(), configuration, path)

  protected def delete(path: RelationalPath[_]): SQLDeleteClause = new SQLDeleteClause(getConnection(), configuration, path)

}
