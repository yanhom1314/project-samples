package demo.boot

import java.io.File

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import demo.actor.{ActorContaints, MonitorStatus}
import ActorContaints._
import demo.actor.MonitorStatus
import demo.actor.MonitorStatus._
import demo.future.CloseRun

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object ClientBoot extends App {
  val clientSystem = ActorSystem(CLIENT_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/call.conf")))

  new Thread(CloseRun(clientSystem)).start()

  //create monitor actor on local
  val miCreate = clientSystem.actorOf(Props[MonitorStatus], "monitorActor")

  clientSystem.scheduler.schedule(3 seconds, 1 seconds) {
    println(s"id:${Thread.currentThread().getId}")
    ACTOR_M.foreach(_._2 ! s"[${System.currentTimeMillis()}].")
  }
}
