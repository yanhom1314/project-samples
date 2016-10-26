package com.example.jdbc.dao

import java.util

import com.example.jdbc.mapper.{Something, SomethingMapper}
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.{Bind, BindBean, SqlQuery, SqlUpdate}

@RegisterMapper(Array(classOf[SomethingMapper]))
trait AnotherQuery {

  @SqlQuery("select * from t_something where id = :id")
  def findById(@Bind("id") id: Int): Something

  @SqlQuery("select * from t_something")
  def findAll(): util.List[Something]

  @SqlUpdate("create or replace table t_something (id int primary key, name varchar(100))")
  def createSomethingTable(): Unit

  @SqlUpdate("insert into t_something(id,name) values(:id,:name)")
  def save(@BindBean d: Something)

  @SqlQuery("select count(*) from t_something")
  def count(): Int

  def close(): Unit
}
