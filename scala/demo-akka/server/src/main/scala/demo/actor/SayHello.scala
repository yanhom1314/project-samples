package demo.actor

import akka.actor.Actor

class SayHello extends Actor with Serializable {
  override def receive: Receive = {
    case msg: String =>
      println(s":SayHello Receive:${msg}")
      sender() ! f"Ok:[${sys.allThreads().size}}]"
    case _ => sender() ! s"SayHello Type: missed."
  }
}
