package demo.actor

import akka.actor.Actor

class SayHello extends Actor with Serializable {
  override def receive: Receive = {
    case msg: String => println(s":SayHello:${msg}")
    case _ => sender() ! s"SayHello Type: missed."
  }
}
