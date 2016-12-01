package sample.actor

import java.text.SimpleDateFormat
import java.util.Date

import akka.actor.Actor

import scala.concurrent.Future

class SlowActor extends Actor {

  import context.dispatcher

  override def receive: Receive = {
    case msg: String =>
      Future {
        val fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        println(s"[${Thread.currentThread().getId}:${fmt.format(new Date())}]:${msg} is begin.")
        Thread.sleep(1000)
        println(s"[${Thread.currentThread().getId}:${fmt.format(new Date())}]:${msg} is end.")
      }
    case _ => sys.error("Not Found!")
  }
}
