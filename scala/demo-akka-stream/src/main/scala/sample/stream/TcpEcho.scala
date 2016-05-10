package sample.stream

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Framing, Sink, Source, Tcp}
import akka.util.ByteString

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object TcpEcho {

  /**
    * Use without parameters to start both client and
    * server.
    *
    * Use parameters `server 0.0.0.0 6001` to start server listening on port 6001.
    *
    * Use parameters `client 127.0.0.1 6001` to start client connecting to
    * server on 127.0.0.1:6001.
    *
    */
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("ServerAndClientSystem")
    implicit val materializer = ActorMaterializer()
    implicit val ex = system.dispatcher

    if (args.isEmpty) {
      val (address, port) = ("127.0.0.1", 6000)
      server(address, port)
      client(address, port)
    } else {
      val (address, port) =
        if (args.length == 3) (args(1), args(2).toInt)
        else ("127.0.0.1", 6000)
      if (args(0) == "server") {
        server(address, port)
      } else if (args(0) == "client") {
        client(address, port)
      }
    }
  }

  def server(address: String, port: Int)(implicit system: ActorSystem, dispatcher: ExecutionContextExecutor, materializer: ActorMaterializer): Unit = {
    val echo = Flow[ByteString]
      .via(Framing.delimiter(
        ByteString("\n"),
        maximumFrameLength = 256,
        allowTruncation = true))
      .map(_.utf8String.toLowerCase.trim)
      .filter {
        case "quit" =>
          system.terminate(); false
        case _ => true
      }
      .map(t => s"[${t}]\n")
      .map(ByteString(_))

    val handler = Sink.foreach[Tcp.IncomingConnection] { conn =>
      println("Client connected from: " + conn.remoteAddress)
      conn handleWith echo
    }

    val connections = Tcp().bind(address, port)
    val binding = connections.to(handler).run()

    binding.onComplete {
      case Success(b) =>
        println("Server started, listening on: " + b.localAddress)
      case Failure(e) =>
        println(s"Server could not bind to $address:$port: ${e.getMessage}")
        system.terminate()
    }

  }

  def client(address: String, port: Int)(implicit system: ActorSystem, dispatcher: ExecutionContextExecutor, materializer: ActorMaterializer): Unit = {

    val testInput = ('a' to 'z').map(ByteString(_))

    val result = Source(testInput).via(Tcp().outgoingConnection(address, port)).
      runFold(ByteString.empty) { (acc, in) ⇒ acc ++ in }

    result.onComplete {
      case Success(result) =>
        println(s"Result:[${result.utf8String}]")
        println("Shutting down client")
        system.terminate()
      case Failure(e) =>
        println("Failure: " + e.getMessage)
        system.terminate()
    }
  }
}
