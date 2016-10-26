package com.example

import com.example.http._
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {

  addFrameworkModules(FreemarkerModule, H2Module)

  override def mustacheModule = MyMustacheModule

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
      .add[DbController]
  }

}
