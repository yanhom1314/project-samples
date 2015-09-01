package demo

import java.io.File

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import demo.MonitorActorSelectionActor._
import demo.MonitorStatus._
import demo.actor.ActorContaints._

import scala.concurrent.duration._

object ClientBoot extends App {
  implicit val system = ActorSystem(CLIENT_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/client.conf")))

  loopByActorOf()
  loopByActorSelection()

  //close after 50 seconds
  system.scheduler.scheduleOnce(120.seconds)(system.shutdown())(system.dispatcher)

  //create monitor actor on local for actorOf
  def loopByActorOf(): Unit = {
    val actor = system.actorOf(Props[MonitorStatus], "monitorActor")
    println(s"actorOf:${actor.path}")
    system.scheduler.schedule(1.seconds, 2.seconds) {
      println(s"###scan ACTORS_QUEUE:${ACTOR_M.size}###")
      ACTOR_M.foreach { e =>
        println(s"name:${e._1} path:${e._2.path} actor:${e._2}")
        e._2 ! s"[${System.currentTimeMillis()}]"
      }
    }(system.dispatcher)
  }

  //create monitor actor on local for actorSelection
  def loopByActorSelection(): Unit = {
    val actor = system.actorOf(Props[MonitorActorSelectionActor], "monitorActorSelectionActor")
    println(s"actorSelection:${actor.path}")
    system.scheduler.schedule(2.seconds, 2.seconds) {
      ACTORS.foreach { e =>
        println(s"name:${e.anchorPath.name} path:${e.anchorPath}")
        e ! s"[${System.currentTimeMillis()}]"
      }
    }(system.dispatcher)
  }
}
