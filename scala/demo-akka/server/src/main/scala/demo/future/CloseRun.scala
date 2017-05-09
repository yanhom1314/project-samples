package demo.future

import java.io.{BufferedReader, InputStreamReader}

import akka.actor.ActorSystem

case class CloseRun(implicit system: ActorSystem) extends Runnable {
  override def run(): Unit = {
    try {
      val reader = new BufferedReader(new InputStreamReader(System.in))

      reader.readLine() match {
        case "quit" | "exit" => system.terminate()
        case _ => Thread.sleep(2000)
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
