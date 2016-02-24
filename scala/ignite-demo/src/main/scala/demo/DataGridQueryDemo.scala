package demo

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.cache.expiry.{CreatedExpiryPolicy, Duration}

import org.apache.ignite.{IgniteCache, Ignition}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

object DataGridQueryDemo extends IgniteApp {
  val fmt = new SimpleDateFormat("yyyyMMddHHmmss")
  println("Hello World!")
  Ignition.setClientMode(true)

  withClientIgnite { ig =>
    val cache: IgniteCache[Int, String] = ig.getOrCreateCache("id_test_map_cache").withExpiryPolicy(new CreatedExpiryPolicy(new Duration(TimeUnit.SECONDS, 5)))

    Future {
      time { () =>
        0 to 50000 foreach { i =>
          cache.put(i, i.toString)
        }
      }
    } onComplete {
      case Success(i) => println(i)
      case Failure(t) => print(t)
    }

    val f1 = Future {
      while (!cache.containsKey(3)) {
        Thread.sleep(1000)
      }
      while (cache.containsKey(3)) {
        0 to 3 foreach { i =>
          println(s"${fmt.format(new Date())}:Got [key = ${i}, val = ${cache.get(i)}]")
        }
        Thread.sleep(1000)
      }
    }

    val f2 = Future {
      while (!cache.containsKey(50000)) {
        Thread.sleep(1000)
      }
      while (cache.containsKey(50000)) {
        49998 to 50000 foreach { i =>
          println(s"${fmt.format(new Date())}:Got [key = ${i}, val = ${cache.get(i)}]")
        }
        Thread.sleep(1000)
      }
    }
    Await.result(f1, 15000.millis)
    Await.result(f2, 15000.millis)

    f1 onSuccess {
      case _ => println(s"key:[3] is expired.")
    }
    f2 onSuccess {
      case _ => println(s"key:[50000] is expired.")
    }

    Thread.sleep(1000)
  }
}
