package demo.oauth2

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import demo.oauth2.http._
import finatra.auto.Hot

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {
  override def defaultFinatraHttpPort = ":80"

  //override protected def modules: Seq[Module] = Array(ShiroModule, FreemarkerModule, H2Module, SpringDataJpaModule)

  override def configureHttp(router: HttpRouter): Unit = {
    Hot.listen("") {
      try {
        ExampleServerMain.exitOnError("Restart!!!!")
      } catch {
        case e: Exception => println(e.getMessage)
      }
      Thread.sleep(2000)
      println("********************")
      ExampleServerMain.start()
      println("####################")
    }

    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[HomeController]
  }
}
