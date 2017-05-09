package demo

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

case object Start

class WorldActor extends Actor {
  def receive = {
    case message: String =>
      println(s">WorldActor#${sender().path} -> ${message}}")
      sender() ! (message.toUpperCase + " world!")
  }
}


class HiActor extends Actor {
  def receive = {
    case message: String =>
      println(s">HiActor#${sender().path} -> ${message}")
      sender() ! (message.toUpperCase + " world!")
  }
}

object LocalApp extends App {
  implicit val system = ActorSystem("demo2ActorSystem")

  val w1 = system.actorOf(Props[WorldActor], "w1")
  val w2 = system.actorOf(Props[HiActor], "w2")

  println("starting...")
  println(s"created action:w1:${w1.path} w2:${w2.path}")

  system.scheduler.scheduleOnce(20.seconds)(system.terminate())(system.dispatcher)
}
