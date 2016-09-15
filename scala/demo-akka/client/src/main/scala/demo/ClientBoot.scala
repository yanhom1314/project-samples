package demo

import java.io.File

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import demo.actor.ActorConstants._

import scala.concurrent.duration._

object ClientBoot extends App {
  implicit val system = ActorSystem(CLIENT_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/client.conf")))

  //  loopByActorOf()
  loopByActorSelection()

  //close after 50 seconds
  system.scheduler.scheduleOnce(40.seconds)(system.terminate())(system.dispatcher)

  //create monitor actor on local for actorOf
  def loopByActorOf(): Unit = {
    val r1 = LoopActorByOf(system)
    system.scheduler.schedule(1.seconds, 3.seconds, r1)(system.dispatcher)
  }

  //create monitor actor on local for actorSelection
  def loopByActorSelection(): Unit = {
    val r2 = LoopActorBySelection(system)
    system.scheduler.schedule(2.seconds, 3.seconds, r2)(system.dispatcher)
  }
}
