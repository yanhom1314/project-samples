import org.apache.geode.cache.client._

import scala.collection.JavaConverters._

object Hello extends App {
  val cache = new ClientCacheFactory()
    .addPoolLocator("localhost", 10334)
    .create()
  val region = cache
    .createClientRegionFactory[String, String](ClientRegionShortcut.CACHING_PROXY)
    .create("region")

  println(s"region size:${region.entrySet().size()}")
  if (region.size() < 1) (1 to 5).foreach { i => region.put(i.toString, i.toString) }

  region.entrySet().asScala.foreach { entry =>
    println(s"key = ${entry.getKey()}, value = ${entry.getValue()}\n");
  }

  cache.close()
}