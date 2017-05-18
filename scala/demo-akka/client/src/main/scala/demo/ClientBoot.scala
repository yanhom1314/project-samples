package demo

import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.{ActorSystem, Cancellable, DeadLetter, Props}
import com.typesafe.config.ConfigFactory
import demo.actor.ActorConstants._
import demo.dead.DeadLetterListener

import scala.concurrent.duration._

object ClientBoot extends App {

  implicit val system = ActorSystem(CLIENT_ACTOR_SYSTEM, ConfigFactory.parseFile(new File("conf/client.conf")))
  implicit val dispatcher = system.dispatcher
  val listener = system.actorOf(Props[DeadLetterListener])
  system.eventStream.subscribe(listener, classOf[DeadLetter])

  //test actor
  //loopByActorSelection()

  //test runnable
  //system.scheduler.schedule(1.second, 2.seconds, RunJob)
  val cancellable = system.scheduler.schedule(1.second, 2.seconds) {
    val fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    println(s"Thread Size:[${Thread.getAllStackTraces().size()}] ${Thread.currentThread().getId} is ok. time:[${fmt.format(new Date)}]")
    Thread.sleep(5000)
  }

  //close after 50 seconds
  system.scheduler.scheduleOnce(40.seconds)(system.terminate())

  //create monitor actor on local for actorSelection
  def loopByActorSelection(): Unit = {
    val r2 = LoopActorBySelection(system)
    system.scheduler.schedule(2.seconds, 3.seconds, r2)(system.dispatcher)
  }
}


case class RunJob(cancellable: Cancellable, max: Long) extends Runnable {
  @volatile var count = 0L

  override def run(): Unit = {
    count += 1
    if (count >= max) {
      cancellable.cancel()
    }
    else {
      val fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      println(s"Thread Size:[${Thread.getAllStackTraces().size()}] ${Thread.currentThread().getId} is ok. time:[${fmt.format(new Date)}]")
    }
    Thread.sleep(3000)
  }
}