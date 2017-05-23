package demo.json

import org.json4s.CustomSerializer
import org.json4s.JsonAST.JNothing

trait Json4sIgnore

object Json4sIgnore {
  val formats = new CustomSerializer[Json4sIgnore](_ => (PartialFunction.empty, {
    case _: Json4sIgnore => JNothing
  }))
}

case class A(a: Int)

case class B(a: A with Json4sIgnore, d: Int)

