package demo.local

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import com.typesafe.config.ConfigFactory
import java.io.File
import scala.concurrent.duration._

class LocalActor extends Actor with ActorLogging {
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@127.0.0.1:5150/user/RemoteActor")
  var count = 0
  def receive = {
    case "START" => remote ! "Hello from the LocalActor"
    case msg: String =>
      println(s"LocalActor receive message:$msg")
      if (count < 5) {
        sender ! s"Hello back to you ${System.currentTimeMillis()}."
        count += 1
      }
  }
}

object LocalActorApp extends App {
  val config = ConfigFactory.parseFile(new File("client/src/main/resources/application.conf"))
  println(s"config:${config}")

  implicit val system = ActorSystem("LocalSystem", config)
  val localActor = system.actorOf(Props[LocalActor], "LocalActor")

  localActor ! "START"

  system.scheduler.scheduleOnce(20.seconds)(system.terminate())(system.dispatcher)
}
