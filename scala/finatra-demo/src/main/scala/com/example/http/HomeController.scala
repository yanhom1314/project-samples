package com.example.http

import javax.inject.{Inject, Singleton}

import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.FormParam
import com.twitter.finatra.response.Mustache
import com.twitter.finatra.validation.{MethodValidation, NotEmpty, Size, ValidationResult}

@Singleton
class HomeController @Inject()(service: ExampleService) extends Controller {

  def list(): List[Person] = List(Person("1", 1, "1"), Person("2", 2, "2"), Person("3", 3, "3"), Person("4", 4, "4"))

  get("/") { _: Request =>
    FooView("YaFengLi", list())
  }

  get("/home") { _: Request =>
    response.ok.html("<h1>Home is Ok!</h1>")
  }

  get("/static/:*") { request: Request =>
    response.ok.fileOrIndex("/static/" + request.params("*"), "/static/index.html")
  }

  get("/foo") { _: Request =>
    FooView("YaFengLi", list())
  }

  get("/d1") { _: Request =>
    Demo1View("YaFengLi", list())
  }
  get("/d2") { _: Request =>
    Demo2View("YaFengLi", list())
  }

  post("/foo") { _: FooRequest =>
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

case class FooRequest(@FormParam name: String,
                      @Size(min = 5, max = 20) @FormParam email: String,
                      @FormParam age: Int,
                      @NotEmpty @FormParam sug: String) {
  @MethodValidation
  def validateName = {
    ValidationResult.validate(name.length < 6, "name length must less 6.")
  }
}

case class Message(message: String)
