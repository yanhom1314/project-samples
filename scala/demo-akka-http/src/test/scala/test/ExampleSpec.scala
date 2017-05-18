package test

import demo.{A, B, Json4sIgnore}
import org.json4s.JsonAST.JNothing
import org.json4s.native.Serialization
import org.json4s.{CustomSerializer, DefaultFormats, Extraction}
import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {
  class C(a:A) extends A(a.a) with Json4sIgnore
  implicit val formats = DefaultFormats +
    new CustomSerializer[Json4sIgnore](_ => (PartialFunction.empty, {
      case _: Json4sIgnore => JNothing
    }))
  "A Stack" should "pop values in last-in-first-out order" in {
    val a = A(1)
    // prints {,"d":2}
    println(Serialization.write(a))
    // prints {"d":2}
    println(Serialization.write(Extraction.decompose(a)))

    val c = new C(a)
    // prints {,"d":2}
    println(Serialization.write(c))
    // prints {"d":2}
    println(Serialization.write(Extraction.decompose(c)))

    val b = B(c, 2)
    // prints {,"d":2}
    println(Serialization.write(b))
    // prints {"d":2}
    println(Serialization.write(Extraction.decompose(b)))
  }

  "A list" should "" in {
    implicit val formats = DefaultFormats
  }
}

