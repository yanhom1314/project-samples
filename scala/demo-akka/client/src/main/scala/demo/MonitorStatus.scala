package demo

import akka.actor._
import demo.actor.ActorContaints._
import demo.actor.{SayHello, SayHi}

import scala.collection.concurrent.TrieMap
import scala.concurrent.duration._

class MonitorStatus extends Actor with ActorLogging {

  import MonitorStatus._

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    //init 
    loadActors(context)(context.system)
  }

  override def receive: Receive = {
    case Terminated(ref) => dead(ref)
    case _ => println("Nothing.")
  }

  private def dead(ref: ActorRef): Unit = {
    println(s"::::dead watch::::actor name:[${ref.path.name}]")
    val name = ref.path.name
    ACTOR_M -= name
    context.unwatch(ref)
    context.system.scheduler.scheduleOnce(10.seconds) {
      if (context != null) ACTOR_M += name -> context.watch(createActor(name, ACTOR_NAMES(name))(context.system))
    }(context.system.dispatcher)
  }
}

object MonitorStatus {
  val ACTOR_NAMES = Map(HI_ACTOR -> classOf[SayHi], HELLO_ACTOR -> classOf[SayHello])
  val ACTOR_M = TrieMap[String, ActorRef]()

  def createActor(name: String, ct: Class[_])(implicit system: ActorSystem): ActorRef = {
    ACTOR_M += (name -> system.actorOf(Props(ct), name)) //use actorOf create new actor
    println(s"NEW ACTOR--->${name} ${ACTOR_M(name).path.name}")
    ACTOR_M(name)
  }

  def loadActors(context: ActorContext)(implicit system: ActorSystem): Unit = {
    ACTOR_NAMES.foreach {
      e =>
        if (!ACTOR_M.contains(e._1)) ACTOR_M += e._1 -> context.watch(createActor(e._1, e._2))
    }
  }
}