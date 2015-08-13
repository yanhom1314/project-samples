package demo

import akka.actor.ActorSystem
import spray.routing.SimpleRoutingApp

object DSLMain extends App with SimpleRoutingApp {

  implicit val system = ActorSystem()

  // the handler actor replies to incoming HttpRequests

  startServer(interface = "localhost", port = 8001) {
    path("hello") {
      get {
        complete {
          <h1>Say hello to spray</h1>
        }
      }
    } ~
      path("index") {
        get {
          complete {
            <h1>Hello World!</h1>
          }
        }
      } ~
      path("stop") {
        get {
          complete {
            system.shutdown()
            <h1>Shutdown 1 second.</h1>
          }
        }
      } ~
      path("sayHi" / IntNumber) { name =>
        post {
          complete {
            <h1>Hello {name}!</h1>
          }
        }
      }
  }
}
