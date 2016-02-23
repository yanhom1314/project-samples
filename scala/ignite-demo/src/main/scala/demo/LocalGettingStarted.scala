package demo

import java.util.Scanner

import org.apache.ignite.{Ignite, Ignition}

object LocalGettingStarted extends App {
  println("Hello World!")
  //val ignite = Ignition.start("config/example-ignite.xml")
  val ignite = Ignition.start()
  println(ignite)

  val ql = QuitListener(ignite)
  ql.setDaemon(true)
  ql.start()
}

case class QuitListener(ignite: Ignite) extends Thread {
  override def run(): Unit = {
    val scanner = new Scanner(System.in)
    var isRun = true
    while (isRun) {
      print(1)
      if (scanner.hasNext) {
        print(2)
        scanner.nextLine().trim.toLowerCase() match {
          case "quit" | "exit" =>
            isRun = false
            ignite.close()
          case _ => print(3)
        }
      }
    }
    println("shutdown ok.")
  }
}








