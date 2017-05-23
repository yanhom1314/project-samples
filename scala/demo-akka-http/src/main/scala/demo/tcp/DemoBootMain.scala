package demo.tcp

import java.net.InetSocketAddress

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.{ByteString, Timeout}

import scala.concurrent.duration._

object DemoBootMain extends App {
  implicit val system = ActorSystem("local")
  implicit val dispatcher = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  val server = system.actorOf(Props[TServer])

  val listener = system.actorOf(Props[ListenerActor])

  val client = system.actorOf(TClient.props(new InetSocketAddress("localhost", 9000), listener))

  println(s"server:${server} client:${client}")
  Thread.sleep(2000)
  val tell = args.length match {
    case n: Int if n >= 1 => true
    case 0 => false
  }

  (0 to 10) foreach { i =>
    if (tell) client ? ByteString(s"${i}?") else client ! ByteString(s"${i}!")
    //client ! ByteString(s"client:${i}")
    //Thread.sleep(1000)
  }
}
