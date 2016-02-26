package com.example

import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import javax.inject.Inject

class PingController @Inject() (service: ExampleService) extends Controller {

  get("/ping") { request: Request =>
    info("ping")
    "pong"
  }

  get("/name") { request: Request =>
    service.myDo(request)
    response.ok.body("Bob")
  }
}
