package com.example

import java.sql.Connection

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import scalikejdbc.DBSession

/**
  * Created by YaFengLi on 2016/6/24.
  */
object HikaricpMain extends Service {
  override def call(conn: Connection)(implicit session: DBSession): Unit = {
    //    val hf = new HikariConfig()
    //    hf.setDataSourceClassName("org.h2.jdbcx.JdbcDataSource")
    //    hf.addDataSourceProperty("user", "sa")
    //    hf.addDataSourceProperty("password", "")
    //    hf.addDataSourceProperty("URL", "jdbc:h2:~/test")

    val hf = new HikariConfig()
    hf.setJdbcUrl("jdbc:h2:~/test")
    hf.setUsername("sa")
    hf.setPassword("")
    hf.setDriverClassName("org.h2.Driver")
    val hd = new HikariDataSource(hf)

    try {
      println(s"hd:${hd}")
      val connection = hd.getConnection
      println(s"connection:${connection}")

    }
    finally {
      hd.close()
    }
  }
}
