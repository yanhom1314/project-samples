package demo

import scala.io.StdIn

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.xml.ScalaXmlSupport._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

object HttpApp extends App {
  print("Hello World!")

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val ex = system.dispatcher
  val route =
    path("hello") {
      get {
        complete {
          <h1>Say hello to akka-http</h1>
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

  closeListener()

  def closeListener(): Unit = {
    StdIn.readLine().toLowerCase().trim match {
      case "quit" | "exit" =>
        bindingFuture
          .flatMap(_.unbind()) // trigger unbinding from the port
          .onComplete(_ ⇒ system.terminate()) // and shutdown when done

      case l =>
        println("Please input quit | exit.")
        closeListener()

    }
  }
}
