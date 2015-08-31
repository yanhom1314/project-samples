package demo

import akka.actor._
import demo.actor.ActorContaints._

import scala.collection.mutable

class MonitorActorSelectionActor extends Actor with ActorLogging {

  import MonitorActorSelectionActor._

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    actors(context.system)
  }

  override def receive: Receive = {
    case Terminated(ref) => println(f"::dead::${ref.path.name}")
    case _ => println("Nothing.")
  }
}

object MonitorActorSelectionActor {
  val PATHS = Seq("akka.tcp://" + REMOTE_ACTOR_SYSTEM + "@127.0.0.1:2554/user/" + HI_ACTOR, "akka.tcp://" + REMOTE_ACTOR_SYSTEM + "@127.0.0.1:2554/user/" + HELLO_ACTOR)
  //val PATHS = Seq("akka.tcp://" + REMOTE_ACTOR_SYSTEM + "@127.0.0.1:2554/user/" + HI_ACTOR)

  val ACTORS = mutable.Buffer[ActorSelection]()

  def actors(implicit system: ActorSystem): Unit = {
    PATHS.foreach { p =>
      val as = system.actorSelection(p)
      ACTORS += as
    }
  }
}