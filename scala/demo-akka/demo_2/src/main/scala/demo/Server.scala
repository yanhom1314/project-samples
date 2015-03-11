package demo

import akka.actor.{Actor, ActorSystem, Props}
import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory

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

class Server extends Bootable {
  val system = ActorSystem("demo2ActorSystem", ConfigFactory.load("demo2"))

  val w1 = system.actorOf(Props[WorldActor], "w1")
  val w2 = system.actorOf(Props[WorldActor], "w2")

  def startup = {
    println("starting...")
    println(s"created action:w1:${w1.path} w2:${w2.path}")
  }

  def shutdown = {
    system.shutdown()
  }
}


