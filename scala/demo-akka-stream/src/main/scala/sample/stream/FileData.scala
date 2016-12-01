package sample.stream

import java.io.File
import java.util.concurrent.LinkedBlockingQueue

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

object FileData {
  def main(args: Array[String]): Unit = {
    // actor system and implicit materializer
    implicit val system = ActorSystem("Sys")
    import system.dispatcher
    implicit val materializer = ActorMaterializer()

    // execution context

    val LoglevelPattern = """.*\s(DEBUG|INFO|WARN|ERROR)\s.*""".r

    // read lines from a log file
    val logFile = new File("e:/tmp/adv_web.log")

    val queue = new LinkedBlockingQueue[Int]()
    queue.iterator()



    val source: Source[Int, NotUsed] = Source(1 to 100)
    val f1 = source.runForeach(i => println(i))
    f1.onComplete { case _ => system.terminate() }
  }
}
