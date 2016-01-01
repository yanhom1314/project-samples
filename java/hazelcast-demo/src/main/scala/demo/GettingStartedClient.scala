package demo

import com.hazelcast.core.IMap
import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig
import scala.collection.JavaConversions._

object GettingStartedClient extends App {
  val config = new ClientConfig()
  val cc = config.getNetworkConfig()
  cc.addAddress("127.0.0.1:5701")
  val client = HazelcastClient.newHazelcastClient(config);
  val map:IMap[Int,String] = client.getMap("customers")

  (0 to 6).foreach { i =>
    Thread.sleep(1000)
    map.foreach( e => println(s"k:${e._1} v:${e._2}"))
    System.out.println(">>>>Map Size:" + map.size())
  }

  Thread.sleep(2000)
  client.shutdown()
}
