package sample.tcp

import akka.actor.{ Actor, ActorLogging }
import akka.io.Tcp

class SimplisticHandler extends Actor with ActorLogging {

  import Tcp._

  override def receive: Receive = {
    case Received(data) =>
      log.error(s"s:${data.utf8String}")
      sender() ! Write(data)
    case PeerClosed =>
      log.error("close.")
      context stop self
  }
}
