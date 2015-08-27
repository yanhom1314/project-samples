package demo.actor

import akka.actor._
import ActorContaints._

import scala.collection.concurrent.TrieMap
import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class MonitorStatus extends Actor with ActorLogging {

  import demo.actor.MonitorStatus._

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    //init 
    loadActors(context)
    //scheduler scan dead actor queue
    context.system.scheduler.schedule(5 seconds, 10 seconds) {
      println(s"###scan ACTORS_QUEUE:${DEAD_Q.size} ${DEAD_Q.isEmpty}###")
      while (!DEAD_Q.isEmpty) {
        val actor = DEAD_Q.dequeue()
        if (context != null) {
          context.stop(actor)
          context.unwatch(actor)
          loadActors(context)
        }
      }
    }
  }

  override def receive: Receive = {
    case Terminated(ref) => dead(ref)
    case _ => println("Nothing.")
  }

  private def dead(ref: ActorRef): Unit = {
    println(s"::::dead watch::::actor ame:[${ref.path.name}]")
    DEAD_Q += ref
    ACTOR_M -= ref.path.name
  }
}

object MonitorStatus {
  val ACTOR_M = TrieMap[String, ActorRef]()

  private val DEAD_Q = mutable.Queue[ActorRef]()

  def createActor(name: String, ct: Class[_], context: ActorContext, args: Any*): ActorRef = {
    ACTOR_M += (name -> context.system.actorOf(if (args.size > 0) Props(ct, args) else Props(ct), name))
    println(s"NEW ACTOR---> ${name}")
    ACTOR_M(name)
  }

  def loadActors(context: ActorContext): Unit = {
    if (!ACTOR_M.contains(HI_ACTOR)) context.watch(createActor(HI_ACTOR, classOf[SayHi], context))
    if (!ACTOR_M.contains(HELLO_ACTOR)) context.watch(createActor(HELLO_ACTOR, classOf[SayHello], context))
  }
}