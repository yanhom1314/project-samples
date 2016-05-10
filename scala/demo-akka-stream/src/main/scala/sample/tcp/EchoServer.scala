package sample.tcp

import java.net.InetSocketAddress

import akka.actor._
import akka.io.Tcp.{Bound, Close, Write}
import akka.io.{IO, Tcp}
import akka.util.ByteString

import scala.concurrent._
import scala.concurrent.duration._

object EchoServer extends App {
  implicit val system = ActorSystem("tcpTest")
  implicit val ex = system.dispatcher

  val server = system.actorOf(Props[Server], "server")
  val listener = system.actorOf(Props[ClientListener], "listener")
  val client = system.actorOf(Props(classOf[Client], new InetSocketAddress("127.0.0.1", 8888), listener), "client")

  try {

    client ! Write(ByteString("123456789abcdefghizklmnopqrstuvwxyz"))
    client ! ByteString("Nice")
    client ! "Gay"
    Thread.sleep(1000)

    Future {
      Thread.sleep(6000)
      client ! "close"
      client ! Close
    } onComplete {
      case _ => system.scheduler.scheduleOnce(10.seconds)(system.terminate())
    }
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {

  }
}

class Server extends Actor with ActorLogging {

  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress(8888))

  override def receive: Receive = {
    case b@Bound(local) => log.error(s"receive:${local.getHostName}:${local.getPort}")
    case CommandFailed(_: Bind) => context stop self
    case c@Connected(remote, local) =>
      val handler = context.actorOf(Props[SimplisticHandler])
      val connection = sender()
      connection ! Register(handler)
  }
}

case class Client(remote: InetSocketAddress, listener: ActorRef) extends Actor with ActorLogging {

  import Tcp._
  import context.system

  IO(Tcp) ! Connect(remote)

  override def receive: Actor.Receive = {
    case CommandFailed(_: Connect) =>
      listener ! "connect failed!"
      context stop self
    case c@Connected(remote, local) =>
      listener ! c
      val connection = sender()
      connection ! Register(self)
      context become {
        case data: ByteString =>
          listener ! "byte string"
          connection ! Write(data)
        case CommandFailed(w: Write) => listener ! "writer failed!"
        case Received(data) =>
          log.error(s"c:${data.utf8String}")
          listener ! data
        case "close" =>
          listener ! "close"
          connection ! Close
        case s: String =>
          listener ! s
          connection ! Write(ByteString(s))
        case _: ConnectionClosed =>
          listener ! "connection closed."
          context stop self
      }
  }
}

class ClientListener extends Actor with ActorLogging {
  override def receive: Actor.Receive = {
    case s: String => log.error(s"str:${s}")
    case bs: ByteString => log.error(s"str[b]:${bs.utf8String}")
    case c@Bound(local) =>
  }
}
