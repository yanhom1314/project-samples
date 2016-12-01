package test

import akka.actor.{ActorSystem, Props}
import akka.stream.ActorMaterializer
import org.scalatest.{FlatSpec, Matchers}
import sample.actor.SlowActor

/**
  * Created by YaFengLi on 2016/5/10.
  */
class ActorSpec extends FlatSpec with Matchers {

  "A Source" should "sink" in {
    implicit val system = ActorSystem("testSystem")
    implicit val materializer = ActorMaterializer()
    implicit val ex = system.dispatcher
    val slowActor = system.actorOf(Props[SlowActor])

    (0 to 20).par.foreach(slowActor ! _.toString)

    Thread.sleep(5000)
    system.terminate()
  }
}
