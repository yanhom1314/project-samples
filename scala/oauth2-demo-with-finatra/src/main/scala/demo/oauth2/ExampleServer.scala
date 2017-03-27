package demo.oauth2

import java.io.File
import java.nio.file.Paths

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import demo.oauth2.http._
import finatra.auto.{Listener, Monitor}

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {
  override def defaultFinatraHttpPort = ":80"

  //override protected def modules: Seq[Module] = Array(ShiroModule, FreemarkerModule, H2Module, SpringDataJpaModule)

  override def configureHttp(router: HttpRouter): Unit = {
    //
    val path = Paths.get(".")
    println(s"path ${path.toFile.getAbsolutePath}")
    val monitor = Monitor(path)
    monitor.watch(new Listener {
      override def onDirectoryChanged(dir: File): Unit = println(s"Directory ${dir.getAbsolutePath} is changed.")

      override def onFileChanged(file: File): Unit = {
        println(s"File ${file.getAbsolutePath} is changed.")
        if (file.getName.endsWith(".class")) {
          try {
            ExampleServerMain.exitOnError("Restart...")
          } catch {
            case e: Exception => e.printStackTrace()
          }
          ExampleServerMain.start()
        }
      }
    })
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[HomeController]
  }
}
