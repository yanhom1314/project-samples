package demo.bean

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Person(val name: String, val age: Int, val address: Option[Address])

case class Address(val address: String, val num: Option[Int])

trait MyJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat = jsonFormat2(Address)
  //must before personFormat
  implicit val personFormat = jsonFormat3(Person)
}

