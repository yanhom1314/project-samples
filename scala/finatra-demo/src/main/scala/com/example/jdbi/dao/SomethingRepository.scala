package com.example.jdbi.dao

import java.util

import com.example.jdbi.mapper.{Something, SomethingMapper}
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.{Bind, BindBean, SqlQuery, SqlUpdate}

@RegisterMapper(Array(classOf[SomethingMapper]))
trait SomethingRepository extends BaseRepository[Something] {
  @SqlUpdate("create table if not exists j_something (id bigint primary key auto_increment, name varchar(100))")
  override def createTable(): Unit

  @SqlUpdate("insert into j_something(name) values(:name)")
  override def save(@BindBean d: Something)

  @SqlUpdate("delete from j_something where id=:id")
  override def delete(d: Something): Unit

  @SqlUpdate("update j_something set name=:name where id=:id")
  override def update(d: Something): Unit

  @SqlQuery("select * from j_something")
  override def findAll(): util.List[Something]

  @SqlQuery("select * from j_something where id = :id")
  override def findById(@Bind("id") id: Long): Something

  @SqlQuery("select count(*) from j_something")
  override def count(): Long
}
