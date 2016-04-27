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

  val r1 =
    path("index") {
      get {
        complete {
          <h2>What the fucking hell!</h2>
        }
      }
    }
  val bindingFuture = Http().bindAndHandle(route ~ r1, host, port)

  println(s"Server online at http://${host}:$port/\nPress [q]uit|[e]xit to stop...")

  closeListener(bindingFuture)

  def closeListener(bindingFuture: Future[Http.ServerBinding]): Unit = {
    StdIn.readLine().toLowerCase().trim match {
      case "q" | "e" =>
        bindingFuture
          .flatMap(_.unbind()) // trigger unbinding from the port
          .onComplete(_ ⇒ system.terminate()) // and shutdown when done
      case l =>
        println(s":${l}:Please input [q]uit | [e]xit.")
        closeListener(bindingFuture)
    }
  }
}
