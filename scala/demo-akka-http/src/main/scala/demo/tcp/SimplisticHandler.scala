package demo.tcp

import akka.actor.Actor
import akka.io.Tcp.{PeerClosed, Received, Write}

class SimplisticHandler extends Actor {
  override def receive: Receive = {
    case Received(data) =>
      println(s"server received:${data.toString()}")
      sender() ! Write(data)
    case PeerClosed => context stop self
  }
}

