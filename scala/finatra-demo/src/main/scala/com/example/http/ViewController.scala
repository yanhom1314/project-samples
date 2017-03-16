package com.example.http

import java.util
import javax.inject.{Inject, Singleton}

import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import finatra.views.freemarker.Freemarker

import scala.collection.JavaConversions._
import scala.collection.mutable

@Singleton
class ViewController @Inject()(service: ExampleService) extends Controller {
  get("/view") { request: Request =>
    val persons = mutable.Buffer[Person]()
    persons += Person("1", 1, "1")
    persons += Person("2", 2, "2")
    persons += Person("3", 3, "3")
    persons += Person("4", 4, "4")

    UserView("YaFengLi", persons.toList)
  }
}

@Freemarker("hello")
case class UserView(name: String, persons: util.List[Person])