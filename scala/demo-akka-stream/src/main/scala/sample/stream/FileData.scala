package sample.stream

import java.io.File

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

object FileData {
  def main(args: Array[String]): Unit = {
    // actor system and implicit materializer
    implicit val system = ActorSystem("Sys")
    implicit val materializer = ActorMaterializer()

    // execution context

    val LoglevelPattern = """.*\s(DEBUG|INFO|WARN|ERROR)\s.*""".r

    // read lines from a log file
    val logFile = new File("e:/tmp/adv_web.log")

    val source: Source[Int, NotUsed] = Source(1 to 100)
    source.runForeach(i => println(i))(materializer)
  }
}
