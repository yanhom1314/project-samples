package example

import java.io.File
import java.nio.file.Paths

import finatra.auto.{FileMonitorListener, Monitor}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.io.StdIn
import scala.util.control.Breaks

object Hello extends Greeting with App {
  val cmd_jar = Array("java", "-cp","""target\scala-2.12\hello-assembly-0.1.0-SNAPSHOT.jar""", "demo.oauth2.ExampleServerMain", "-admin.port=:8000", "-http.port=:80")
  val cmd_class = Array("java", "-Djava.ext.dirs=target\\universal\\stage\\lib", "-cp", "target/scala-2.12/classes", "demo.oauth2.ExampleServerMain", "-admin.port=:8000", "-http.port=:80")

  println(greeting)


  //Await.ready(listener(), 5.seconds)
  Await.ready(Monitor.monitor(Paths.get("e:/tmp/result/"), new FileMonitorListener), 30.seconds)

  def listener(): Future[Unit] = {
    Future {
      var p = run()
      while (true) {
        StdIn.readLine().trim.toLowerCase() match {
          case "quit" =>
            p.destroy()
            Breaks.break()
          case "restart" =>
            p.destroy()
            p = run()
          case s: String => println(s"NOT COMMAND :[${s}]!!!")
        }
      }
    }
  }

  @throws[Exception]
  def runJar(): Process = {
    val pb = new ProcessBuilder(cmd_jar: _*)
    pb.redirectOutput(new File("e:/tmp/output.txt"))
    pb.redirectError(new File("e:/tmp/error.txt"))
    pb.start()
  }

  def run(): Process = {
    val pb = new ProcessBuilder(cmd_class: _*)
    pb.redirectOutput(new File("e:/tmp/output.txt"))
    pb.redirectError(new File("e:/tmp/error.txt"))
    pb.start()
  }
}

trait Greeting {
  lazy val greeting: String = "hello"
}
