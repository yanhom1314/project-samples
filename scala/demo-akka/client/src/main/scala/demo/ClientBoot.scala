package demo

import java.io.File

import akka.actor.{ActorSystem, DeadLetter, Props}
import com.typesafe.config.ConfigFactory
import demo.actor.ActorConstants._
import demo.dead.DeadLetterListener

import scala.concurrent.duration._

object ClientBoot extends App {

  implicit val system = ActorSystem(CLIENT_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/client.conf")))
  val listener = system.actorOf(Props[DeadLetterListener])
  system.eventStream.subscribe(listener, classOf[DeadLetter])

  loopByActorSelection()

  //close after 50 seconds
  system.scheduler.scheduleOnce(40.seconds)(system.terminate())(system.dispatcher)

  //create monitor actor on local for actorSelection
  def loopByActorSelection(): Unit = {
    val r2 = LoopActorBySelection(system)
    system.scheduler.schedule(2.seconds, 3.seconds, r2)(system.dispatcher)
  }
}
