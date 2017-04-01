package com.example

import com.example.http._
import com.google.inject.Module
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import finatra.greatbit.module.{BeetlModule, FreemarkerModule, ShiroModule, SpringDataJpaModule}

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {
  override def defaultFinatraHttpPort = ":80"

  override protected def modules: Seq[Module] = Array(FreemarkerModule, BeetlModule, H2Module, SpringDataJpaModule, ShiroModule)

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
      .add[SecurityController]
  }
}
