package com.example.http

import javax.inject.{Inject, Singleton}

import com.example.jpa.AnotherThing
import com.example.jpa.repo.AnotherThingRepository
import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import finatra.freemarker.Freemarker
import finatra.views.beetl.Beetl

import scala.collection.JavaConverters._

@Singleton
class ViewController @Inject()(service: ExampleService, anotherThingRepository: AnotherThingRepository) extends Controller {
  val persons = List(Person("1", 1, "1", List(Friend("11", 11), Friend("111", 111))),
    Person("2", 2, "2", List(Friend("22", 22), Friend("222", 222))),
    Person("3", 3, "3", List(Friend("33", 33), Friend("333", 333))))
  get("/freemarker/hello") { _: Request =>
    HelloView("YaFengLi", persons)
  }
  get("/beetl/hi") { _: Request =>
    HiView(anotherThingRepository.findAll().asScala.toList)
  }

  get("/twirl/simple") { _: Request =>
    response.ok.html(html.simple.render("hello").body)
  }
  get("/twirl/hello") { _: Request =>
    response.ok.html(html.hello.render("yafengli.dream@gmail.com").body)
  }
}

@Freemarker("hello")
case class HelloView(name: String, persons: List[Person])

@Beetl("beetl/hi")
case class HiView(things: List[AnotherThing])
