package keys.event.listener

import java.util.Scanner

import sun.misc.{Signal, SignalHandler}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object KeysListener {
  private lazy val scanner = new Scanner(System.in)
  private val handler = new SignalHandler() {
    override def handle(signal: Signal): Unit = println("Got signal:" + signal)
  }

  def watch(): Unit = {
    Signal.handle(new Signal("INT"), handler)
    Future {
      while (scanner.hasNext) {
        val line = scanner.next()
        println(s"line:${line}")
      }
    }
  }
}
