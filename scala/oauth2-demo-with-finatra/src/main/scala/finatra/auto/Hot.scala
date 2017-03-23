package finatra.auto


import scala.concurrent.Future
import scala.io.StdIn

object Hot {
  def listen(path: String)(restart: => Unit): Unit = {
    import scala.concurrent.ExecutionContext.Implicits.global
    Future {
      while (true) {
        val s = StdIn.readLine()
        println(s"stdin:${s}")
        if (s.equalsIgnoreCase("quit")) {
          //sys.exit(0)
          restart
        }
      }
    }
  }
}
