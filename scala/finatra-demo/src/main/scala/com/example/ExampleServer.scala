package com.example

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.filter.{TraceIdMDCFilter, LoggingMDCFilter}
import com.twitter.finatra.logging.modules.Slf4jBridgeModule

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {

  override def modules = Seq(Slf4jBridgeModule)

  override def defaultFinatraHttpPort = ":9988"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PingController]
  }
}
