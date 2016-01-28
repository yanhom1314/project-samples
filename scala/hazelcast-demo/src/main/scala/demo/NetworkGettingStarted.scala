package demo

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig
import com.hazelcast.core.IMap

import scala.collection.JavaConversions._

object NetworkGettingStarted extends App {
  val config = new ClientConfig()
  val cc = config.getNetworkConfig()
  cc.addAddress("127.0.0.1:5701")
  implicit val instance = HazelcastClient.newHazelcastClient(config)
  val map: IMap[Int, String] = instance.getMap("customers")

  (0 to 6).foreach { i =>
    Thread.sleep(1000)
    System.out.println(s"###############size:${map.size}##############")
    map.foreach(e => println(s"k:${e._1} v:${e._2}"))
    System.out.println("########################################")
  }

  Thread.sleep(2000)
  instance.shutdown()
}
