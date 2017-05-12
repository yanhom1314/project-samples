package demo

import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import json4s.support.Json4sSupport._
import org.json4s.{DefaultFormats, native}

object Json4sHttp {
  final case class Foo(bar: String, age: Int)
  implicit val serialization = native.Serialization // or native.Serialization
  implicit val formats = DefaultFormats

  def route(implicit mat: Materializer) = {
    path("json4s") {
      post {
        entity(as[Foo]) { foo =>
          complete {
            foo
          }
        }
      } ~ get {
        complete {
          Foo("Hello World", 12)
        }
      }
    }
  }
}
