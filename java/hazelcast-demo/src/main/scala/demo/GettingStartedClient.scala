package demo

import com.hazelcast.client.config.ClientConfig
import com.hazelcast.client.HazelcastClient
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.IMap

object GettingStartedClient extends App{
  val clientConfig = new ClientConfig
  clientConfig.addAddress("127.0.0.1:5701")
  val client = HazelcastClient.newHazelcastClient(clientConfig);
  val map = client.getMap("customers")
  System.out.println("Map Size:" + map.size())
}
