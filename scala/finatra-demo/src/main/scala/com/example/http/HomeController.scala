package com.example.http

import java.util
import java.util.Locale
import javax.inject.{Inject, Singleton}

import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{FormParam, Header}
import com.twitter.finatra.response.Mustache
import com.twitter.finatra.validation.{MethodValidation, NotEmpty, Size, ValidationResult}

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

@Singleton
class HomeController @Inject()(service: ExampleService) extends Controller {

  def list(): List[Person] = List(Person("1", 1, "1", List(Friend("11", 11), Friend("111", 111))),
    Person("2", 2, "2", List(Friend("22", 22), Friend("222", 222))),
    Person("3", 3, "3", List(Friend("33", 33), Friend("333", 333))))

  get("/") { _: Request =>
    IndexView("YaFengLi", list())
  }

  get("/home") { _: Request =>
    response.ok.html("<h1>Home is Ok!</h1>")
  }

  get("/static/:*") { request: Request =>
    response.ok.fileOrIndex("/static/" + request.params("*"), "/static/index.html")
  }

  get("/foo") { _: Request =>
    IndexView("YaFengLi", list())
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

@Mustache("index")
case class IndexView(name: String, persons: List[Person])

@Mustache("d1")
case class Demo1View(name: String, persons: List[Person])

@Mustache("d2")
case class Demo2View(name: String, persons: List[Person])

case class Friend(@BeanProperty name: String, @BeanProperty age: Int)

case class Person(@BeanProperty name: String, @BeanProperty age: Int, @BeanProperty address: String, @BeanProperty friends: List[Friend])

case class FooRequest(@Header `Accept-Language`: Option[String] = None,
                      @FormParam name: String,
                      @Size(min = 5, max = 20) @FormParam email: String,
                      @FormParam age: Int,
                      @NotEmpty @FormParam sug: String, req: Request) {
  @MethodValidation
  def validateName = {
    def locales = `Accept-Language`.fold(Seq(Locale.getDefault())) { lang =>
      Locale.LanguageRange.parse(lang).asScala.map(f => Locale.forLanguageTag(f.getRange))
    }

    locales.foreach(println(_))

    println(req)

    ValidationResult.validate(name.length < 6, "name length must less 6.")
  }
}

case class Message(message: String)
