package sample.stream

import java.io.File

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.io.Framing
import akka.stream.scaladsl._
import akka.util.ByteString

object GroupLogFile {

  val fileSeperator = "\n"

  def main(args: Array[String]): Unit = {
    // actor system and implicit materializer
    implicit val system = ActorSystem("Sys")
    implicit val materializer = ActorMaterializer()

    // execution context

    val LoglevelPattern = """.*\s(DEBUG|INFO|WARN|ERROR)\s.*""".r

    // read lines from a log file
    val logFile = new File("e:/tmp/adv_web.log")

    Source.file(logFile).
      // parse chunks of bytes into lines
      via(Framing.delimiter(ByteString(fileSeperator), maximumFrameLength = 8094, allowTruncation = true)).
      map(_.utf8String).
      // group them by log level
      groupBy {
        case LoglevelPattern(level) => level
        case other                  => "OTHER"
      }.

      // write lines of each group to a separate file
      mapAsync(parallelism = 5) {
        case (level, groupFlow) =>
          groupFlow.map(line => ByteString(line + System.lineSeparator())).runWith(Sink.file(new File(s"e:/tmp/target/log-$level.txt")))
      }.
      runWith(Sink.onComplete { _ =>
        system.shutdown()
      })
  }
}
