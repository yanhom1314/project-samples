package com.example.http

import javax.inject.Inject

import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class PingController @Inject() (service: ExampleService) extends Controller {

  get("/ping") { request: Request =>
    info("ping")
    "pong" + System.currentTimeMillis()
  }

  get("/name") { request: Request =>
    request.headerMap.foreach { t =>
      println(s"${t._1}:${t._2}")
    }
    service.myDo(request)
    response.ok.plain("Bob")
  }
}
