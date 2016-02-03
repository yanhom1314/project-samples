package com.example

import java.util.Calendar
import java.text._

import com.example.H2DB._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Hello {
  val create_drop_sql =
    """
      |CREATE OR REPLACE TABLE t_person(
      |id INT PRIMARY KEY,
      |name VARCHAR(255),
      |address VARCHAR(300));
    """.stripMargin

  val insert_sql = "INSERT INTO t_person(id,name,address) VALUES(?,?,?)"

  val query_sql = "SELECT * FROM t_person WHERE id > ?"

  val fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    init()

    //create_table()
    //clear()
    insert_data()

    withConnection { c =>
      val q = c.prepareStatement(query_sql)
      q.setInt(1, 1)
      val rs = q.executeQuery()
      println(s"rs:${rs}")
      while (rs.next()) {
        println(s"id:${rs.getInt("id")}")
      }
    }

    Future {
      shutdown()
    }
  }

  def create_table(): Unit = {
    withConnection { c =>
      println(s"connection:${c}")
      val p1 = c.prepareStatement(create_drop_sql)
      val r1 = p1.executeUpdate()
      println(s"result:${r1}")
    }
  }

  def insert_data(): Unit = {
    time { () =>
      withConnection { c =>
        val base = Calendar.getInstance().get(Calendar.MINUTE) * 1000
          (0 to 1000).foreach { i =>
            val p = c.prepareStatement(insert_sql)
            p.setInt(1, base + i)
            p.setString(2, s"${base}:${i}")
            p.setString(3, s"NanJing-${i}")
            val r = p.executeUpdate()
          }
      }
    }
  }

  def clear(): Unit = {
    withConnection(c => c.prepareStatement("DELETE FROM t_person").executeUpdate())
  }

  def time(call: => Unit):Unit = {
    val start  = System.currentTimeMillis()
    try{
      call
    }
    catch {
      case e:Exception => e.printStackTrace()
    }
    val end  = System.currentTimeMillis()
    println(s"time:${end - start}ms.")
  }
}
