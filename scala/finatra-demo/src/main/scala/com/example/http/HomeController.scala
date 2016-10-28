package com.example.http

import javax.inject.Inject

import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.FormParam
import com.twitter.finatra.response.Mustache
import com.twitter.finatra.validation.{ MethodValidation, NotEmpty, Size, ValidationResult }

import scala.collection.mutable.Buffer

class HomeController @Inject() (service: ExampleService) extends Controller {

  get("/home") { request: Request =>
    response.ok.html("<h1>Home is Ok!</h1>")
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

  get("/d1") { request: Request =>
    val persons = Buffer[Person]()
    persons += Person("1", 1, "1")
    persons += Person("2", 2, "2")
    persons += Person("3", 3, "3")
    persons += Person("4", 4, "4")

    Demo1View("YaFengLi", persons.toList)
  }
  get("/d2") { request: Request =>
    val persons = Buffer[Person]()
    persons += Person("1", 1, "1")
    persons += Person("2", 2, "2")
    persons += Person("3", 3, "3")
    persons += Person("4", 4, "4")

    Demo2View("YaFengLi", persons.toList)
  }

  post("/foo") { request: FooRequest =>
    Message("这是搞什么飞机啊！！")
  }
}

@Mustache("foo")
case class FooView(name: String, persons: List[Person])

@Mustache("d1")
case class Demo1View(name: String, persons: List[Person])

@Mustache("d2")
case class Demo2View(name: String, persons: List[Person])

case class Person(name: String, age: Int, address: String)

case class FooRequest(
  @FormParam name: String,
  @Size(min = 5, max = 20)@FormParam email: String,
  @FormParam age: Int,
  @NotEmpty @FormParam sug: String) {
  @MethodValidation
  def validateName = {
    ValidationResult.validate(name.length < 6, "name length must less 6.")
  }
}

case class Message(message: String)
