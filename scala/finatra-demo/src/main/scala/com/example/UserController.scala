package com.example

import com.twitter.finagle.http.Cookie
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.JsonIgnoreBody
import com.twitter.finatra.request.QueryParam
import com.twitter.finatra.request.RequestInject
import com.twitter.finatra.response.Mustache
import com.twitter.finatra.validation.Max
import com.twitter.finatra.validation.PastTime
import javax.inject.Inject
import org.joda.time.DateTime
import scala.collection.mutable.Buffer

class UserController @Inject() (service: ExampleService) extends Controller {

  get("/users") { request: UsersRequest =>
    println(request.http.headerMap)
    UsersResponse(request.max, request.startDate, request.verbose)
  }

  get("/users/:id") { request: Request =>
    "You looked up " + request.params("id")
  }
}

case class UsersRequest(
  @Max(100) @QueryParam max: Int,
  @PastTime @QueryParam startDate: Option[DateTime],
  @QueryParam verbose: Boolean = false,
  @JsonIgnoreBody @RequestInject http: Request
)

case class UsersResponse(max: Int, startDate: Option[DateTime], verbose: Boolean = false)
