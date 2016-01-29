package demo

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

import scala.concurrent.duration._

object RouteMain extends App with SimpleRoutingApp with DSLRouteService with HelloService {
  implicit val system = ActorSystem("my-system")

  startServer(interface = "localhost", port = 8080) {
    dslRoutes ~ helloRoute ~
      path("stop") {
        complete {
          system.scheduler.scheduleOnce(1.second)(system.terminate())(system.dispatcher)
          <h1>Shutdown 1 second.</h1>
        }
      } ~
      complete {
        <html>
          <body>
            <ul>
              <li>
                <a href="/hello">/hello</a>
              </li>
              <li>
                <a href="/dsl/red?name=YaFengLi">/dsl/color?name=*</a>
              </li>
              <li>
                <a href="/index">/index</a>
              </li>
              <li>
                <a href="/twirl/index/Hello">/twirl/index/@message</a>
              </li>
              <li>
                <a href="/profile/12/YaFengLi">/profile/@id/@name</a>
              </li>
              <li>
                <a href="/context/index.html">/context/@page.name</a>
                [E:/Github/project-samples/web/angularjs/*]
              </li>
              <li>
                <a href="/stop">/stop</a>
              </li>
            </ul>
          </body>
        </html>
      }
  }
}