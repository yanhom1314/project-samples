package com.example.jdbi.dao

import java.util

import com.example.jdbi.mapper.{User, UserMapper}
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper
import org.skife.jdbi.v2.sqlobject.{Bind, BindBean, SqlQuery, SqlUpdate}

@RegisterMapper(Array(classOf[UserMapper]))
trait UserRepository extends BaseRepository[User] {
  @SqlUpdate("create or replace table j_user (id int primary key, username varchar(100),password varchar(100),age int,address varchar(100))")
  override def createTable(): Unit

  @SqlUpdate("insert into j_user(id,username,password,age,address) values(:id, :username, :password, :age, :address)")
  override def save(@BindBean d: User): Unit

  @SqlUpdate("delete from j_user where id=:id")
  override def delete(@BindBean d: User): Unit

  @SqlUpdate("update j_user set username=:username,password=:password,age=:age,address=:address where id=:id")
  override def update(@BindBean d: User): Unit

  @SqlQuery("select * from j_user")
  override def findAll(): util.List[User]

  @SqlQuery("select * from j_user where id=:id")
  override def findById(@Bind("id") id: Long): User

  @SqlQuery("select count(*) from j_user")
  override def count(): Long
}
