package com.example.http

import javax.inject.Inject

import com.example.freemarker.Freemarker
import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
  * Created by LYF on 2016/10/23.
  */
class ViewController @Inject()(service: ExampleService) extends Controller {
  get("/view") { request: Request =>
    UserView("YaFengLi")
  }
}

@Freemarker("hello")
case class UserView(name: String)