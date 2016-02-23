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
    println(scanner.hasNext)
    while (isRun) {
      scanner.nextLine().trim.toLowerCase() match {
        case "quit" | "exit" =>
          isRun = false
          ignite.close()
        case _ => println("2:" + scanner.hasNext)
      }
    }
    println("shutdown ok.")
  }
}








