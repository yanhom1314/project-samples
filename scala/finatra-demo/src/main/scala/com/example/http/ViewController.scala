package com.example.http

import java.util
import javax.inject.{Inject, Singleton}

import com.example.jpa.AnotherThing
import com.example.jpa.repo.AnotherThingRepository
import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import finatra.views.beetl.Beetl
import finatra.views.freemarker.Freemarker

import scala.collection.JavaConverters._

@Singleton
class ViewController @Inject()(service: ExampleService, anotherThingRepository: AnotherThingRepository) extends Controller {
  val persons = List(Person("1", 1, "1", List(Friend("11", 11), Friend("111", 111)).asJava),
    Person("2", 2, "2", List(Friend("22", 22), Friend("222", 222)).asJava),
    Person("3", 3, "3", List(Friend("33", 33), Friend("333", 333)).asJava)).asJava
  get("/freemarker/hello") { _: Request =>
    HelloView("YaFengLi", persons)
  }
  get("/beetl/hi") { _: Request =>
    HiView(anotherThingRepository.findAll().asScala.toList.asJava)
  }

  get("/twirl/simple") { _: Request =>
    response.ok.html(html.simple.render("hello").body)
  }
  get("/twirl/hello") { _: Request =>
    response.ok.html(html.hello.render("yafengli.dream@gmail.com").body)
  }
}

@Freemarker("hello")
case class HelloView(name: String, persons: util.List[Person])

@Beetl("beetl/hi")
case class HiView(things: util.List[AnotherThing])
