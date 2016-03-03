package message

import java.util.Scanner

import akka.actor.ActorSystem

import scala.concurrent.Future
import scala.util.{Failure, Success}

case class CloseListener(val system: ActorSystem) extends Thread {
  override def run = {
    try {
      val scan = new Scanner(System.in)
      var isRun = true
      while (isRun) {
        if (scan.hasNextLine()) {
          scan.nextLine().toLowerCase().trim() match {
            case "quit" | "exit" =>
              isRun = false
              system.terminate()
            case _ =>
          }
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
