package com.example

import com.twitter.finagle.http.Request

case class ExampleService(name: String) {
  def this() = this("Hello World!")
  def myDo(req: Request): Unit = {
    println(s"req:${req}")
  }
}
