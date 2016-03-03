package demo.actor

import akka.actor.Actor
import akka.actor.ActorLogging

class HelloRemoteActor extends Actor with ActorLogging {

  def receive = {
    case msg: String =>
      println(s"receive:$msg")
      sender ! "Hello " + System.currentTimeMillis()
    case _ => log.error(s"Not String Type MESSAGE!!!")
  }
}
