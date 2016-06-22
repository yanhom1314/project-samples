package demo.querydsl

import com.querydsl.core.types._
import com.querydsl.scala._

import com.querydsl.core.types.PathMetadataFactory._;

import com.querydsl.scala.sql.RelationalPathImpl

import com.querydsl.sql._

object QMembers extends QMembers("members") {
  override def as(variable: String) = new QMembers(variable)
  
}

class QMembers(md: PathMetadata) extends RelationalPathImpl[Members](md, "PUBLIC", "MEMBERS") {
  def this(variable: String) = this(forVariable(variable))

  def this(parent: Path[_], property: String) = this(forProperty(parent, property))

  val createdAt = createDateTime[java.sql.Timestamp]("createdAt")

  val id = createNumber[Int]("id")

  val name = createString("name")

  val constraint6: PrimaryKey[Members] = createPrimaryKey(id)

  addMetadata(createdAt, ColumnMetadata.named("CREATED_AT").ofType(93).withSize(23).withDigits(10).notNull())
  addMetadata(id, ColumnMetadata.named("ID").ofType(4).withSize(10).notNull())
  addMetadata(name, ColumnMetadata.named("NAME").ofType(12).withSize(64))
}

