package demo

import akka.actor.ActorSystem
import akka.util.Timeout
import demo.actor.ActorConstants._

import scala.concurrent.duration._
import scala.util.{Failure, Success}

case class LoopActorBySelection(val system: ActorSystem) extends Runnable {
  implicit val timeout = Timeout(5.seconds)
  implicit val dispatch = system.dispatcher

  override def run(): Unit = {
    val hiSelectActor = system.actorSelection(s"akka.tcp://${REMOTE_ACTOR_SYSTEM}@${HOST}:${PORT}/user/${HI_ACTOR}").resolveOne()
    val helloSelectActor = system.actorSelection(s"akka.tcp://${REMOTE_ACTOR_SYSTEM}@${HOST}:${PORT}/user/${HELLO_ACTOR}").resolveOne()
    val message = s"[${System.currentTimeMillis()}]"
    hiSelectActor.onComplete {
      case Success(hi) => hi ! message
      case Failure(_) => println(">>>>>>>>>>>>>>NO HI ACTOR SELECTION!!!")
    }
    helloSelectActor.onComplete {
      case Success(hello) => hello ! message
      case Failure(_) => println(">>>>>>>>>>>>>>NO HELLO ACTOR SELECTION!!!")
    }
  }
}
