package demo

import akka.actor.ActorSystem
import demo.actor.ActorConstants._

case class LoopActorBySelection(val system: ActorSystem) extends Runnable {
  val hiSelectActor = system.actorSelection(s"akka.tcp://${REMOTE_ACTOR_SYSTEM}@${HOST}:${PORT}/user/${HI_ACTOR}")
  val helloSelectActor = system.actorSelection(s"akka.tcp://${REMOTE_ACTOR_SYSTEM}@${HOST}:${PORT}/user/${HELLO_ACTOR}")
  var isOk = true

  override def run(): Unit = {
    if (isOk) {
      println(s"actorSelection: hiSelectActor->${hiSelectActor.pathString} helloSelectActor->${helloSelectActor.pathString} ok:${isOk}")
      hiSelectActor ! s"[${System.currentTimeMillis()}]"
      helloSelectActor ! s"[${System.currentTimeMillis()}]"
    }
  }
}
