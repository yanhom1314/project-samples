package com.example

import java.sql.Connection

import scalikejdbc._

/**
  * Created by YaFengLi on 2016/6/22.
  */
object InitH2DBMain extends Service {

  override def call(conn: Connection)(implicit session: DBSession): Unit = {
    sql"""
       |create table members (
       |  id serial not null primary key,
       |  name varchar(64),
       |  created_at timestamp not null
       |);
       |create table t_person (
       | id  serial not null primary key,
       | name varchar(100),
       | age INT
       |);
      """.stripMargin.execute().apply()

    Seq("Alice", "Bob", "Chris") foreach { name =>
      sql"insert into members (name, created_at) values (${name}, current_timestamp)".update.apply()
    }

    Seq("Alice", "Bob", "Chris") foreach { name =>
      sql"insert into t_person (name, age) values (${name}, ${Math.random() * 200})".update.apply()
    }
  }
}
