package demo

import org.apache.ignite.{Ignition,IgniteCache}

object DataGridQueryDemo extends IgniteApp {
  println("Hello World!")
  Ignition.setClientMode(true)

  withClientIgnite { ig =>
    val cache:IgniteCache[Int,String] = ig.getOrCreateCache("id_test_map_cache")

    time { () =>
      0 to 50000 foreach { i =>
        cache.put(i,i.toString)
      }
    }
    time { () =>
      0 to 100 foreach  {i =>
        println(s"Got [key = ${i}, val = ${cache.get(i)}]")
      }
    }
  }
}
