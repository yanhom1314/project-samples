package demo.boot

import java.io.File

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import demo.actor.ActorConstants._
import demo.actor.{SayHello, SayHi}

import scala.concurrent.duration._

object ServerBoot extends App {
  implicit lazy val system = ActorSystem(REMOTE_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/server.conf")))

  create()

  def create(): Unit = {
    (system.actorOf(Props[SayHello], HELLO_ACTOR), system.actorOf(Props[SayHi], HI_ACTOR)) match {
      case (a1, a2) => println(s"a1:${a1.path} a2:${a2.path}")
      case _ =>
    }
  }

  //shutdown
  system.scheduler.scheduleOnce(15.seconds)(system.terminate())(system.dispatcher)
}
