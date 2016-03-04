package com.example

import com.twitter.finatra.validation.NotEmpty
import com.twitter.finatra.validation.Size
import scala.collection.mutable.Buffer

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.FormParam
import com.twitter.finatra.response.Mustache
import com.twitter.finatra.validation.{MethodValidation, ValidationResult}
import javax.inject.Inject

class HomeController @Inject() (service: ExampleService) extends Controller {

  get("/home") { request: Request =>
    response.ok.body("<h1>Home is Ok!</h1>")
  }

  get("/static/:*") { request: Request =>
    response.ok.fileOrIndex("/static/" + request.params("*"), "/static/index.html")
  }

  get("/foo") { request: Request =>
    val persons = Buffer[Person]()
    persons += Person("1", 1, "1")
    persons += Person("2", 2, "2")
    persons += Person("3", 3, "3")
    persons += Person("4", 4, "4")

    FooView("YaFengLi", persons.toList)
  }

  post("/foo") { request: FooRequest =>
    Message("这是搞什么飞机啊！！")
  }
}

@Mustache("foo")
case class FooView(name: String, persons: List[Person])

case class Person(name: String, age: Int, address: String)

case class FooRequest(
  @FormParam name: String,
  @Size(min = 5, max = 20)@FormParam email: String,
  @FormParam age: Int,
  @NotEmpty @FormParam sug: String
) {
  @MethodValidation
  def validateName = {
    ValidationResult.validate(name.length < 6, "name length must less 6.")
  }
}

case class Message(message: String)