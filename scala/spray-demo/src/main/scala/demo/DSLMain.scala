package demo

import java.io.File

import akka.actor.ActorSystem
import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes
import spray.httpx.encoding.Gzip
import spray.routing.SimpleRoutingApp

object DSLMain extends App with SimpleRoutingApp {

  implicit val system = ActorSystem()

  // the handler actor replies to incoming HttpRequests
  val route =
    parameter('color) { color =>
      complete(s"The color is '$color'")
    }


  startServer(interface = "localhost", port = 8001) {
    get {
      path("hello") {
        parameter('color, 'name ? "NOTHING") { (color, name) =>
          respondWithHeader(RawHeader("ADD_HEAD_KEY", "Hello")) {
            respondWithMediaType(MediaTypes.`text/html`) {
              encodeResponse(Gzip) {
                complete {
                  s"""<h1>Say hello to spray,
                     |<span>${color}</span>
                     |<span>${name}</span>!</h1>""".stripMargin('|')
                }
              }
            }
          }
        }
      } ~
        path("index") {
          complete {
            <ul>
              <li>
                <h1>Hello World!</h1>
              </li>
              <li>
                <a href="/hello?color=blue">Hello?color=blue</a>
              </li>
              <li>
                <a href="/hello?color=blue&amp;name=YaFengli">Hello?color=blue
                  &amp;
                  name=YaFengli</a>
              </li>
              <li>
                <a href="/profile/1/Second">Profile/$1/$2</a>
              </li>
              <li>
                <form action="/stop" method="POST">
                  <input type="submit" value="Stop"/>
                </form>
              </li>
            </ul>
          }
        } ~
        pathPrefix("twirl") {
          path("index" / Segment) { message =>
            respondWithMediaType(MediaTypes.`text/html`) {
              encodeResponse(Gzip) {
                complete {
                  page.html.index(message).toString
                }
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
            system.shutdown()
            <h1>Shutdown 1 second.</h1>
          }
        }
      }
  }
}
