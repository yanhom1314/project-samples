package com.example

import com.example.http.{HomeController, PingController, UserController, ViewController}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {

  addFrameworkModule(FreemarkerModule)

  override def mustacheModule = MyMustacheModule

  //override protected def messageBodyModule = MyMessageBodyModule

  override val disableAdminHttpServer = true

  override def defaultFinatraHttpPort = ":80"

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PingController]
      .add[HomeController]
      .add[UserController]
      .add[ViewController]
  }
}
