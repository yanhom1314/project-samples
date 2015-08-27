package demo

import java.io.File

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

import demo.future.CloseRun
import demo.actor.ActorContaints._
import demo.actor.MonitorStatus

object ClientBoot extends App {
  implicit val system = ActorSystem(CLIENT_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/call.conf")))

  new Thread(CloseRun(system)).start()

  //create monitor actor on local
  val miCreate = system.actorOf(Props[MonitorStatus], "monitorActor")

  system.scheduler.schedule(3.seconds, 1.second)({
    println(s"id:${Thread.currentThread().getId}")
    MonitorStatus.ACTOR_M.foreach(_._2 ! s"[${System.currentTimeMillis()}].")
  })(system.dispatcher)
}
