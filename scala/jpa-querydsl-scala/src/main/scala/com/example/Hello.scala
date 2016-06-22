package com.example
import scala.io._
import java.io.File

object Hello {
  def main(args: Array[String]): Unit = {
    println(s"Hello, world name:${args}!")
    Source.fromFile(new File("build.sbt")).getLines.foreach(println(_))
  }
}
