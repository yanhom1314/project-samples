package demo.boot

import akka.actor._
import com.typesafe.config.ConfigFactory
import demo.actor.HelloRemoteActor
import java.io.File
import scala.concurrent.ExecutionContext.Implicits.global
import message.CloseListener

object HelloRemoteApp extends App {
  val server = new File("server/src/main/resources/server.conf")
  val config = ConfigFactory.parseFile(server)
  println(s"config:${config}")

  implicit val system = ActorSystem("HelloRemoteSystem", config)
  val remoteActor = system.actorOf(Props[HelloRemoteActor], name = "RemoteActor")

  println(s"path:${remoteActor.path}")

  remoteActor ! "The RemoteActor is alive."

  CloseListener(system).start()
}
