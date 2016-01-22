package demo

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

object Main extends App {

  implicit val system = ActorSystem()

  // the handler actor replies to incoming HttpRequests
  val handler = system.actorOf(Props[DemoService], name = "handler")

  val service =new DemoService()
  println(fibonacci(10))
  println(fibonacci(22))
  println(service.getClass().getName())
  IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8001)
  def fibonacci(n: Int): Long = {
    n match {
      case 0 => 0L
      case 1 => 1L
      case _ => fibonacci(n-1) + fibonacci(n-2) 
    }
  }

  println(fibonacci(23))
  println(service.getClass().getName())
}
