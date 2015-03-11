package demo.boot

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ConsoleDemo extends App {


  loop()

  def loop(): Unit = {
    Future {
      val in = Console.in
      while (in.read() != 4) {
        Thread.sleep(1000)
      }
      println("ctrl+d:quit")
      //System.exit(0)
    }
  }
}
