package demo

import org.apache.ignite.cache.CacheMode
import org.apache.ignite.cache.query.SqlFieldsQuery
import org.apache.ignite.configuration.CacheConfiguration
import org.apache.ignite.{Ignite, Ignition}


object CacheQueryDemo extends App {
  Ignition.setClientMode(true)

  withIgnite { ig =>
    val orgCfg = new CacheConfiguration[Long, Organization]("orgCache")
    orgCfg.setCacheMode(CacheMode.REPLICATED)
    orgCfg.setIndexedTypes(classOf[Long], classOf[Organization])

    val orgCache = ig.getOrCreateCache(orgCfg)
    (0 to 10).foreach { i =>
      val org =new  Organization(i.toLong, i.toString)
      orgCache.put(org.getId, org)
    }

    //Get the names of Organization

    val sql = """select p.id, p.name from "orgCache".Organization as p where p.name = ?"""
    val cursor = orgCache.query(new SqlFieldsQuery(sql).setArgs("2"))

    println(s"cursor:${cursor.getAll}")
  }

  def withIgnite(call: Ignite => Unit): Unit = {
    val ignite = Ignition.start("config/example-ignite.xml")
    try {
      call(ignite)
    }
    catch {
      case e: Exception => e.printStackTrace()
    } finally {
      ignite.close()
    }
  }
}
