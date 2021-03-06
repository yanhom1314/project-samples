package com.example.http

import javax.inject.{Inject, Singleton}

import com.example.filter.UserFilter
import com.example.service.ExampleService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{JsonIgnoreBody, QueryParam}
import com.twitter.finatra.validation.{Max, PastTime}
import org.joda.time.DateTime
@Singleton
class UserController @Inject()(service: ExampleService) extends Controller {

  get("/users") { request: UsersRequest =>
    request.http.params.foreach(t => println(s"${t._1} -> ${t._2}"))
    request.http.headerMap.foreach(t => println(s"${t._1} -> ${t._2}"))

    UsersResponse(request.max, request.startDate, request.verbose)
  }

  filter[UserFilter].get("/users/:id") { request: Request =>
    import com.example.filter.UserContext._
    response.ok.html("You looked up " + request.params("id") + s" user id:${request.user.id}")
  }
}

case class UsersRequest(@Max(100) @QueryParam max: Int,
                        @PastTime @QueryParam startDate: Option[DateTime],
                        @QueryParam verbose: Boolean = false,
                        @JsonIgnoreBody @Inject() http: Request)

case class UsersResponse(max: Int, startDate: Option[DateTime], verbose: Boolean = false)
