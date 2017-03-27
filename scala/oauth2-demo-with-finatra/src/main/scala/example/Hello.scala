package example

import java.io.File
import java.nio.file.Paths

import finatra.auto.{FileSystemListener, Monitor}

object Hello extends Greeting {
  val cmd_jar = Array("java", "-cp","""target\scala-2.12\hello-assembly-0.1.0-SNAPSHOT.jar""", "demo.oauth2.ExampleServerMain", "-admin.port=:8000", "-http.port=:80")
  val cmd_class = Array("java", "-Djava.ext.dirs=target\\universal\\stage\\lib", "-cp", "target/scala-2.12/classes", "demo.oauth2.ExampleServerMain", "-admin.port=:8000", "-http.port=:80")

  def main(args: Array[String]): Unit = {
    println(greeting)
    val monitor = Monitor(Paths.get("e:/tmp/result/"))
    monitor.watch(new FileSystemListener)

    //Await.ready(listener(), 30.seconds)
    Thread.sleep(30000)
    monitor.terminate()
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
