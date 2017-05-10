package demo

import akka.actor.{ActorSystem, Props}
import demo.actor.ActorConstants._
import demo.actor.{SayHello, SayHi}

case class LoopActorByOf(val system: ActorSystem) extends Runnable {
  val hiActor = system.actorOf(Props[SayHi], HI_ACTOR)
  val helloActor = system.actorOf(Props[SayHello], HELLO_ACTOR)

  override def run(): Unit = {
    println(s"actorOf: hiActor:[${hiActor.path}] helloActor:[${helloActor.path}]")
    hiActor ! s"[${System.currentTimeMillis()}]"
    helloActor ! s"[${System.currentTimeMillis()}]"
  }
}
