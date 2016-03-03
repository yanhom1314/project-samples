package demo.local

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import java.io.File
import java.io.InputStream
import java.net.InetAddress
import message.{CloseListener, Tip}
import scala.collection.mutable
import scala.io.Source

class LocalActor extends Actor with ActorLogging {
  val remote = context.actorSelection("akka.tcp://HelloRemoteSystem@127.0.0.1:5150/user/RemoteActor")
  var count_1 = 0
  var count_2 = 0
  def receive = {
    case "START" =>
      remote ! "Hello from the LocalActor"
      remote ! Tip(InetAddress.getLocalHost.getHostName, System.currentTimeMillis())
    case msg: String =>
      println(s"LocalActor receive message:$msg")
      if (count_1 < 3) {
        sender ! s"Hello back to you ${System.currentTimeMillis()}."
        count_1 += 1
      }
    case tip: Tip =>
      println(s"LocalActor receive tip message:${tip.name}")
      if (count_2 < 10) {
        sender ! Tip(tip.name, System.currentTimeMillis())
        count_2 += 1
      }
  }
}

object LocalActorApp extends App {
  val config = ConfigFactory.parseFile(new File("client/src/main/resources/application.conf"))
  //val config = ConfigFactory.parseResources(this.getClass.getClassLoader, "application.conf")

  println(s"config:${config}")

  implicit val system = ActorSystem("LocalSystem", config)
  val localActor = system.actorOf(Props[LocalActor], "LocalActor")

  localActor ! "START"

  CloseListener(system).start()

  val str = withInputStream("application.conf")(println(_))

  println("##################")
  println(str)

  def withInputStream(name: String)(call: String => Unit): String = {
    val buffer = mutable.Buffer[String]()
    var input: InputStream = null
    try {
      input = this.getClass.getClassLoader.getResourceAsStream(name)

      Source.fromInputStream(input).getLines.foreach { l =>
        call(l)
        buffer + l
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (input != null) input.close()
    }
    buffer.mkString(System.lineSeparator)
  }
}
