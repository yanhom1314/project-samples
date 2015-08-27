package demo.actor

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, OneForOneStrategy, SupervisorStrategy}

class SayHi extends Actor {

  override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
    case _ =>
      println("Restart")
      Restart
  }

  override def receive: Receive = {
    case msg: String => println(s":SayHi:${msg}")
    case _ => sender() ! s"SayHi Type: missed."
  }
}
