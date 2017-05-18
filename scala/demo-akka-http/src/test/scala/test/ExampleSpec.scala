package test

import org.json4s.JsonAST.JNothing
import org.json4s.native.Serialization
import org.json4s.{CustomSerializer, DefaultFormats, Extraction}
import org.scalatest._

class ExampleSpec extends FlatSpec with Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    implicit val formats = DefaultFormats +
      new CustomSerializer[Ignore](_ => (PartialFunction.empty, {
        case _: Ignore => JNothing
      }))

    val a = A(1)
    // prints {,"d":2}
    println(Serialization.write(a))
    // prints {"d":2}
    println(Serialization.write(Extraction.decompose(a)))

    val c = C(a)
    // prints {,"d":2}
    println(Serialization.write(c))
    // prints {"d":2}
    println(Serialization.write(Extraction.decompose(c)))

    val b = B(a, 2)
    // prints {,"d":2}
    println(Serialization.write(b))
    // prints {"d":2}
    println(Serialization.write(Extraction.decompose(b)))
  }
}

