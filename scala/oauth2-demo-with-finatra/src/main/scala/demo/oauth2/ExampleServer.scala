package demo.oauth2

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import demo.oauth2.http._

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {
  override def defaultFinatraHttpPort = ":80"

  //override protected def modules: Seq[Module] = Array(ShiroModule, FreemarkerModule, H2Module, SpringDataJpaModule)

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[HomeController]
  }
}
