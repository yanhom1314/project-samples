package demo

import akka.actor.ActorSystem
import spray.http.{HttpHeader, HttpHeaders}
import spray.routing.SimpleRoutingApp

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.xml.Elem

object HelloMain extends App with SimpleRoutingApp {

  implicit val system = ActorSystem()

  def headHost: HttpHeader => Option[String] = {
    case h: HttpHeaders.`Host` =>
      println("host:" + h.host + ":" + h.port)
      Some(h.port.toString)
    case _ =>
      println("host:")
      None
  }

  def headUa: HttpHeader => Option[String] = {
    case ua: HttpHeaders.`User-Agent` =>
      println("ua:" + ua.value)
      Some(ua.value)
    case _ =>
      println("ua:")
      None
  }

  def headAp: HttpHeader => Option[String] = {
    case a: HttpHeaders.Accept =>
      println("a:" + a.value)
      Some(a.value)
    case _ =>
      println("ap")
      None
  }

  val stop =
    <form action="stop" method="post">
      <input type="submit" value="Stop"/>
    </form>

  def html: String => Elem = { t =>
    <div>
      <h3>Hello World! port:[
        {t}
        ]</h3>
      <form action="stop" method="post">
        <input type="submit" value="Stop"/>
      </form>
    </div>
  }

  val route = get {
    optionalHeaderValue(headHost) {
      case Some(port) =>
        optionalHeaderValue(headAp) {
          case Some(ap) => complete {
            println("1")
            html(ap)
          }
          case None => complete {
            println("2")
            stop
          }
        }
      case None => complete {
        stop
      }
    }
  } ~ post {
    path("stop") {
      complete {
        Future {
          Thread.sleep(200)
          system.shutdown()
        }
        <h3>The system is shutdown...</h3>
      }
    }
  }


  startServer("127.0.0.1", 80) {
    route
  }
}
