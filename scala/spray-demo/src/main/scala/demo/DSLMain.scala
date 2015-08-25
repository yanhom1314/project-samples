package demo

import java.io.File

import akka.actor.ActorSystem
import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes
import spray.httpx.encoding.Gzip
import spray.routing.SimpleRoutingApp

import scala.concurrent.duration._

object DSLMain extends App with SimpleRoutingApp {
  implicit val system = ActorSystem("my-system")
  startServer(interface = "localhost", port = 8001) {
    get {
      path("hello") {
        parameter('color, 'name ? "NOTHING") { (color, name) =>
          respondWithHeader(RawHeader("ADD_HEAD_KEY", "Hello")) {
            respondWithMediaType(MediaTypes.`text/html`) {
              encodeResponse(Gzip) {
                complete {
                  s"<h1>Say hello to spray,<span>${color}</span><span>${name}</span>!</h1>"
                }
              }
            }
          }
        }
      } ~
        path("index") {
          complete {
            <h1>Hello world!</h1>
          }
        } ~
        pathPrefix("twirl") {
          path("index" / Segment) { message =>
            respondWithMediaType(MediaTypes.`text/html`) {
              complete {
                page.html.index(message).toString
              }
            }
          }
        } ~
        path("profile" / IntNumber / Segment) { (id, name) =>
          complete {
            <h1>
              <span>
                {id}
              </span>
              <span>
                {name}
              </span>
            </h1>
          }
        } ~
        path("file" / Segment) { name =>
          getFromFile(new File("E:/Github/project-samples/web/angularjs", name))
        }
    } ~
      post {
        path("stop") {
          complete {
            system.scheduler.scheduleOnce(1.second)(system.shutdown())(system.dispatcher)
            <h1>Shutdown 1 second.</h1>
          }
        }
      }
  }
}
