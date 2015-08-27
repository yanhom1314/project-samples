package demo.boot

import java.io.File

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import demo.actor.ActorContaints._
import demo.actor.{SayHello, SayHi}
import demo.future.CloseRun

object ServerBoot extends App {
  implicit lazy val system = ActorSystem(REMOTE_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/remote.conf")))

  new Thread(CloseRun(system)).start()

  create() match {
    case (a1, a2) => println(s"a1:${a1.path.toString} a2:${a2.path.toString}")
    case _ =>
  }

  def create(): (ActorRef, ActorRef) = {
    (system.actorOf(Props[SayHello], HELLO_ACTOR), system.actorOf(Props[SayHi], HI_ACTOR))
  }
}
