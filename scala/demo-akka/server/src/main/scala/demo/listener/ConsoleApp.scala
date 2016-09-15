package demo.listener

import java.io.{BufferedReader, InputStreamReader}

import akka.actor.ActorSystem

import scala.concurrent.Future

object ConsoleApp extends App {
  implicit val system = ActorSystem("consoleSystem")

  import system.dispatcher

  //  val f = new Frame("test")
  //
  //  def initFrame(): Unit = {
  //    f.addKeyListener(new KeyListener {
  //
  //      override def keyTyped(e: KeyEvent): Unit = {
  //        KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK)
  //        println(s"code:${e.getKeyCode}")
  //        System.in.
  //      }
  //
  //      override def keyPressed(e: KeyEvent): Unit = {}
  //
  //      override def keyReleased(e: KeyEvent): Unit = {}
  //
  //
  //    })
  //    f.pack()
  //    f.setVisible(true)
  //  }
  //
  //  initFrame()

  event {
    println("exit.")
    system.terminate()
    sys.exit(0)
  }
  println(msg)

  def event(call: => Unit): Unit = {
    Future {
      var isRun = true
      val reader = new BufferedReader(new InputStreamReader(System.in))
      while (isRun) {
        reader.read() match {
          case 4 | 3 => // ctrl+d = 4
            isRun = false
            call
          case code: Int => println(s"command code:${code}")
        }
      }
    }
  }
}
