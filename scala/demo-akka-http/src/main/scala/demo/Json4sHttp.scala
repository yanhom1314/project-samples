package demo

import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import json4s.support.Json4sSupport._
import org.json4s.{DefaultFormats, Extraction, native}

object Json4sHttp {

  final case class Foo(bar: String, age: Int)

  implicit val serialization = native.Serialization // or native.Serialization


  def route(implicit mat: Materializer) = {
    pathPrefix("json4s") {
      path("foo") {
        implicit val formats = DefaultFormats
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
      } ~ path("a") {
        implicit val formats = DefaultFormats
        get {
          complete(A(1))
        }
      } ~ path("b") {
        implicit val fmt = DefaultFormats + Json4sIgnore.formats
        complete(Extraction.decompose(B(new C(A(2)), 1)))
      }
    }
  }
}

class C(a: A) extends A(a.a) with Json4sIgnore

