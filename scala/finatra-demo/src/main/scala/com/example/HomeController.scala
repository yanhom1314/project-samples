package com.example

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.response.Mustache
import javax.inject.Inject
import scala.collection.mutable.Buffer

class HomeController @Inject() (service: ExampleService) extends Controller {

  get("/home") { request: Request =>
    response.ok.body("<h1>Home is Ok!</h1>")
  }


  get("/file") { request: Request =>
    response.ok.file("/public/file123.txt")
  }

  get("/foo") { request: Request =>
    val persons = Buffer[Person]()
    persons += Person("1",1,"1")
    persons += Person("2",2,"2")
    persons += Person("3",3,"3")
    persons += Person("4",4,"4")

    FooView("YaFengLi",persons.toList)
  }
}

@Mustache("foo")
case class FooView(name: String,persons:List[Person])


case class Person(name:String,age:Int,address:String)
