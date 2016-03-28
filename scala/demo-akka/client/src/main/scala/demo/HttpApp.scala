package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.io.StdIn

object HttpApp extends App {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val ex = system.dispatcher

  val host = "localhost"
  val port = 80

  val route =
    path("hello") {
      get {
        complete {
          <h1>Say hello to akka-http</h1>
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, host, port)

  println(s"Server online at http://${host}:$port/\nPress [quit|exit] to stop...")

  closeListener(bindingFuture)

  def closeListener(bindingFuture: Future[Http.ServerBinding]): Unit = {
    StdIn.readLine().toLowerCase().trim match {
      case "quit" | "exit" =>
        bindingFuture
          .flatMap(_.unbind()) // trigger unbinding from the port
          .onComplete(_ ⇒ system.terminate()) // and shutdown when done
      case l =>
        println(s":${l}:Please input quit | exit.")
        closeListener(bindingFuture)
    }
  }
}
