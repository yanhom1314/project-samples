package demo.actor

import akka.actor.Actor
import akka.actor.ActorLogging
import message.Tip
class HelloRemoteActor extends Actor with ActorLogging {

  def receive = {
    case msg: String =>
      println(s"receive:$msg")
      sender ! "Hello " + System.currentTimeMillis()
    case tip:Tip =>
      println(s"receive Tip:${tip.name}")
      sender ! tip
    case _ => log.error(s"Not String Type MESSAGE!!!")
  }
}
