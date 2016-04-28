package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import demo.bean.{Address, MyJsonSupport, Person}

import scala.concurrent.Future
import scala.io.StdIn

object HttpApp extends App with MyJsonSupport {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val ex = system.dispatcher

  val host = "localhost"
  val port = 80

  val route =
    get {
      pathPrefix("hello" / Segment) { name =>
        path(Segment) { age =>
          complete {
            HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, s"""<h1>Hello, Mr.$name age is $age !!!</h1>"""))
          }
        }
      }
    }

  val r1 =
    get {
      path("index") {
        parameter('color ? "red") { color =>
          parameter('name ? "NB") { name =>
            complete(s"The color is '$color' and the background is $name")
          }
        }
      }
    }

  val r2 =
    get {
      path("say") {
        parameters("color" ? "red", 'name ? "NB") { (color, name) =>
          complete(s"Say!!! The color is '$color' and the background is $name")
        }
      }
    }

  val r3 =
    get {
      path("hi" / Segment / Segment) { (name, age) =>
        complete {
          HttpResponse(entity = HttpEntity(ContentTypes.`text/html(UTF-8)`,s"""<h2>Nice Hello,Mr. $name age is $age !!!!!!</h2>"""))
        }
      }
    }

  var r4 =
    get {
      path("person" / Segment / IntNumber) { (name, age) =>
        complete(Person(name, age))
      }
    }

  val bindingFuture = Http().bindAndHandle(route ~ r1 ~ r2 ~ r3 ~ r4, host, port)

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
