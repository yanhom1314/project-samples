package demo.tcp

import java.net.InetSocketAddress

import akka.actor.{Actor, Props}
import akka.io._

class TServer extends Actor {

  import Tcp._
  import context.system

  val manager = IO(Tcp)

  manager ! Bind(self, new InetSocketAddress("localhost", 9000))

  override def receive: Receive = {
    case b@Bound(localAddress) => println(s"server bound:${localAddress.getHostName}:${localAddress.getPort}")
    case CommandFailed(_: Bind) => context stop self
    case c@Connected(remote, local) =>
      println(s"remote:${remote.getHostName}:${remote.getPort} local:${local.getHostName}:${local.getPort} connected:${c.toString}")
      val handler = context.actorOf(Props[SimplisticHandler])
      val connect = sender()
      connect ! Register(handler)
  }
}
