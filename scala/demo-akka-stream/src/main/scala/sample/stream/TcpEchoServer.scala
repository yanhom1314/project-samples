package sample.stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.io.Framing
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.duration._

object TcpEchoServer extends App {
  implicit val system = ActorSystem("tcpEchoServer")
  implicit val materializer = ActorMaterializer()

  Tcp().bind("0.0.0.0", 8888).runForeach { connection =>
    val echo = Flow[ByteString]
      .via(Framing.delimiter(ByteString(System.lineSeparator()), maximumFrameLength = 2048, allowTruncation = true))
      .map(_.utf8String)
      .filter(l =>
      if (l.equalsIgnoreCase("quit")) {
        system.scheduler.scheduleOnce(1.second)(system.shutdown())(system.dispatcher)
        false
      } else true)
      .map(_ + "!!" + System.lineSeparator())
      .map(ByteString(_))
    connection.handleWith(echo)
  }
}
