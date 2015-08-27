package demo.boot

import java.io.File

import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import demo.actor.{ActorContaints, SayHello, SayHi}
import ActorContaints._
import demo.actor.{SayHello, SayHi}
import demo.future.CloseRun

object ServerBoot extends App {
  lazy val serverSystem = ActorSystem(REMOTE_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/remote.conf")))

  new Thread(CloseRun(serverSystem)).start()

  create() match {
    case (a1, a2) => println(s"a1:${a1.path.toString} a2:${a2.path.toString}")
    case _ =>
  }

  def create(): (ActorRef, ActorRef) = {
    (serverSystem.actorOf(Props[SayHello], HELLO_ACTOR), serverSystem.actorOf(Props[SayHi], HI_ACTOR))
  }
}
