package demo

import java.util.{Map, Queue}

import com.hazelcast.config._
import com.hazelcast.core._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent._

object ScalaGettingStarted extends App {
  val cfg = new Config()
  val instance = Hazelcast.newHazelcastInstance(cfg)
  val mapCustomers: Map[Int, String] = instance.getMap("customers")
  mapCustomers.put(1, "Joe")
  mapCustomers.put(2, "Ali")
  mapCustomers.put(3, "Avi")

  println("Customer with key 1: " + mapCustomers.get(1))
  println("Map Size:" + mapCustomers.size())

  val queueCustomers: Queue[String] = instance.getQueue("customers")
  queueCustomers.offer("Tom")
  queueCustomers.offer("Mary")
  queueCustomers.offer("Jane")
  println("First customer: " + queueCustomers.poll())
  println("Second customer: " + queueCustomers.peek())
  println("Queue size: " + queueCustomers.size())

  Future {
    Thread.sleep(20000)
    instance.shutdown()
    println("Shutdown...")
  }
}
