package demo

import demo.data.RadiusJava
import org.apache.geode.cache.client._
import org.apache.geode.cache.query.SelectResults

import scala.collection.JavaConverters._

object QueryMain extends App {
  val cache = new ClientCacheFactory()
    .addPoolLocator("localhost", 10334)
    .create()
  val region = cache
    .createClientRegionFactory[String, RadiusJava](ClientRegionShortcut.CACHING_PROXY)
    .create("region")

  println(s"region size:${region.entrySet().size()}")
  if (region.size() < 1) (1 to 5).foreach { i => region.put(i.toString,new  RadiusJava(i.toString, "1.1.1.1", i.toString)) }

  region.entrySet().asScala.foreach { entry =>
    println(s"key = ${entry.getKey()}, value = ${entry.getValue()}\n");
  }

  val query = cache.getQueryService().newQuery("SELECT * FROM /region WHERE un = $1")
  val params = Array[AnyRef]("1")
  val result = query.execute(params).asInstanceOf[SelectResults[String]]
  println(result.size())
  println(result.asSet().asScala)
  cache.close()
}