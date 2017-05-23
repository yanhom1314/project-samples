package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import demo.http.{Json4sHttp, SprayJsonHttp}

import scala.io.StdIn

object WebServer {
  val BASE_DIR = if (System.getProperty("http.path") != null) System.getProperty("http.path") else System.getProperty("user.dir") + "/html"

  val HTTP_PORT = if (System.getProperty("http.port") != null) System.getProperty("http.port").toInt else 80


  def main(args: Array[String]) {
    implicit val system = ActorSystem("my-demo-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route = SprayJsonHttp.route ~ Json4sHttp.route

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", HTTP_PORT)

    println(s"Server[${BASE_DIR}] online at http://localhost:${HTTP_PORT}/\nPress ENTER to stop...")
    StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}