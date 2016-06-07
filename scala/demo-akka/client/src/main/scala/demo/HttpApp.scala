package demo

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import demo.bean.{Address, MyJsonSupport, Person}

import scala.concurrent.Future
import scala.io.StdIn

object HttpApp extends App with MyJsonSupport {
  val `application/javascript(UTF-8)` = MediaTypes.`application/javascript` withCharset HttpCharsets.`UTF-8`
  lazy val cacheJs = scala.io.Source.fromURL(new java.net.URL("https://cdn.jsdelivr.net/jquery/2.2.4/jquery.min.js")).mkString

  val `path/jquery` = Uri.Path("/jquery.min.js")
  val addJs =
    """
      |$(function(){$("#message").text(Math.random()*1000);});
    """.stripMargin

  implicit val system = ActorSystem("my-system", ConfigFactory.parseResources(this.getClass.getClassLoader, "common.conf"))
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
        complete(Person(name, age, None))
      } ~ path("address" / Segment / IntNumber) { (a, n) =>
        complete(Address(a, Some(n)))
      }
    }

  val r5: Route = { ctx =>
    val path = ctx.request.uri.path
    println(s"path:${path} -> ${path.startsWith(`path/jquery`)}")
    if (ctx.request.method == HttpMethods.GET && path.startsWith(`path/jquery`))
      ctx.complete {
        HttpResponse(entity = HttpEntity(`application/javascript(UTF-8)`, cacheJs + addJs))
      }
    else
      ctx.complete(HttpResponse(status = StatusCodes.NotFound, entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, s"${ctx.request.uri.path} Not found.")))
  }

  val bindingFuture = Http().bindAndHandle(route ~ r1 ~ r2 ~ r3 ~ r4 ~ r5, host, port)

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
