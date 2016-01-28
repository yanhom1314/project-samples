package demo

import java.util.concurrent.TimeUnit
import java.util.{Queue, Scanner}

import com.hazelcast.config._
import com.hazelcast.core._

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent._

object LocalGettingStarted extends App {
  val cfg = new Config()
  implicit val instance = Hazelcast.newHazelcastInstance(cfg)

  val mapCustomers: IMap[Int, String] = instance.getMap("customers")
  mapCustomers.put(1, "Joe")
  mapCustomers.put(2, "Ali")
  mapCustomers.put(3, "Avi")
  mapCustomers.put(4, "Nice", 4, TimeUnit.SECONDS)
  mapCustomers.put(5, "Hello", 6, TimeUnit.SECONDS)

  println("####Customer with key 1: " + mapCustomers.get(1))
  println("####Map Size:" + mapCustomers.size())

  val queueCustomers: Queue[String] = instance.getQueue("customers")
  queueCustomers.offer("Tom")
  queueCustomers.offer("Mary")
  queueCustomers.offer("Jane")
  println("#####First customer: " + queueCustomers.poll())
  println("#####Second customer: " + queueCustomers.peek())
  println("#####Queue size: " + queueCustomers.size())

  Future {
    Thread.sleep(1000)
    quit(new Scanner(System.in))
  }

  def quit(scan: Scanner)(implicit instance: HazelcastInstance): Unit = {
    val line = scan.nextLine()
    line.trim().toLowerCase() match {
      case "quit" =>
        instance.shutdown()
        println("Shutdown...")
      case _ =>
        println(line)
        quit(scan)
    }
  }
}
