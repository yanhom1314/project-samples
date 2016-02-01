package demo.route

import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes
import spray.httpx.encoding.Gzip
import spray.routing.HttpService

trait DSLRouteService extends HttpService {
  val dslRoutes = path("dsl" / Segment) { color =>
    parameter('name ? "NOTHING") { name =>
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
    path("context" / Segment) { name =>
      getFromFile(new File("E:/Github/project-samples/web/angularjs", name))
    }
}
