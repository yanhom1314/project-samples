package demo.bean

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Person(val name: String, val age: Int, val address: Address = Address("",""))

case class Address(val addr: String, val num: String)

trait MyJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val addressFormat = jsonFormat2(Address) //must before personFormat
  implicit val personFormat = jsonFormat3(Person)
}

