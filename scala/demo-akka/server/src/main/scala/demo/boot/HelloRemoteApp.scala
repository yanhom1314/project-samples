package demo.boot

import akka.actor._
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigResolveOptions
import demo.actor.HelloRemoteActor
import java.io.File
import java.util.Scanner
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.util.Failure
import scala.util.Success

object HelloRemoteApp extends App {
  val server = new File("server/src/main/resources/server.conf")
  val config = ConfigFactory.parseFile(server)
  println(s"config:${config}")

  implicit val system = ActorSystem("HelloRemoteSystem", config)
  val remoteActor = system.actorOf(Props[HelloRemoteActor], name = "RemoteActor")

  println(s"path:${remoteActor.path}")

  remoteActor ! "The RemoteActor is alive."

  Future {
    try {
      val scan = new Scanner(System.in)
      var isRun = true
      while (isRun) {
        if (scan.hasNextLine()) {
          scan.nextLine().toLowerCase().trim() match {
            case "quit" | "exit" =>
              isRun = false
              system.terminate()
            case _ =>
          }
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  } onComplete {
    case Success(p) => println("End " + System.currentTimeMillis())
    case Failure(t) => println("Error")
  }
}
