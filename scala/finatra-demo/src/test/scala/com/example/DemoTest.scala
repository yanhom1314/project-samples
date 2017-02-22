package com.example

import org.scalatest.{FlatSpec, Matchers}

import scala.sys.process._

class DemoTest extends FlatSpec with Matchers {
  "A Stack" should "pop values in last-in-first-out order" in {
    val r = """^$""".r
    println("Hello, world!")
    r.findFirstMatchIn("123123")

    val lines = "vmstat" #|| "nothing" lineStream

    for (l <- lines) {
      val ss = l.trim().split(" +")
      ss.foreach { c => print(s"${c} ") }
      println(s"${ss.length}:${ss.mkString("\\t")}")
    }

    view(lines.last.trim.split(" +"))
  }

  def view(ss: Array[String]): Unit = {

    if (ss.length >= 17) {
      println(s"${ss(11)} ${ss(12)} = ${ss(11).toInt + ss(12).toInt}")
    }
  }
}
