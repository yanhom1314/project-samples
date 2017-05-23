package demo.tcp

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString

object TClient {
  def props(remote: InetSocketAddress, replies: ActorRef) = Props(classOf[TClient], remote, replies)
}

class TClient(remote: InetSocketAddress, listener: ActorRef) extends Actor {

  import Tcp._
  import context.system

  IO(Tcp) ! Connect(remote)

  override def receive: Receive = {
    case CommandFailed(_: Connect) => listener ! "connect failed!!!"
    case c@Connected(remote, local) => listener ! c
      println(s"client: remote:${remote.toString} local:${local.toString}")
      val connect = sender()

      connect ! Register(self)
      context.become {
        case data: ByteString =>
          connect ! Write(data)
        case CommandFailed(w: Write) =>
          listener ! "O/S buffer was full"
        case Received(data) =>
          listener ! data
        case Close =>
          connect ! Close
        case _: ConnectionClosed =>
          listener ! "connection closed!!!"; context stop self
        case o: Any =>
          println(s"Client Any:${o.toString}")
      }
  }
}

class ListenerActor extends Actor {
  override def receive: Receive = {
    case s: String => println(s"client string:${s}")
    case data: ByteString => println(s"client data:${data}")
    case o: Any => println(s"client any:${o.toString}")
  }
}