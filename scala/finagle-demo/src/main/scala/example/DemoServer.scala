package example

import java.util.concurrent.TimeUnit

import com.twitter.finagle.{Http, Service, http}
import com.twitter.util
import com.twitter.util.{Await, Future}

import scala.io.StdIn

object DemoServer extends App {
  val service = new Service[http.Request, http.Response] {
    def apply(req: http.Request): Future[http.Response] = {
      Future.value(
        http.Response(req.version, http.Status.Ok)
      )
    }
  }

  val server = Http.serve(":8080", service)

  StdIn.readLine()
  server.close(util.Duration(1, TimeUnit.SECONDS))
}