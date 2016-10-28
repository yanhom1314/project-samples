package com.example

import com.example.http._
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finagle.oauth2._
import com.twitter.finatra._
import com.example.filter.OAuthDataHandler

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {
  addFrameworkModules(FreemarkerModule, H2Module, SpringDataJpaModule)

  override def mustacheModule = MyMustacheModule

  override val disableAdminHttpServer = true

  override def defaultFinatraHttpPort = ":80"

  override def configureHttp(router: HttpRouter): Unit = {
    val dataHandler = new OAuthDataHandler 
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
