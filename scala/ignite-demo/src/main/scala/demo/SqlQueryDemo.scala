package demo

import org.apache.ignite.Ignition
import org.apache.ignite.cache.CacheMode
import org.apache.ignite.cache.query.SqlFieldsQuery
import org.apache.ignite.configuration.CacheConfiguration

object SqlQueryDemo extends IgniteApp {
  Ignition.setClientMode(true)

  withClientIgnite { ig =>
    val orgCfg = new CacheConfiguration[Long, Organization]("orgCache")
    orgCfg.setCacheMode(CacheMode.REPLICATED)
    orgCfg.setIndexedTypes(classOf[Long], classOf[Organization])

    val orgCache = ig.getOrCreateCache(orgCfg)

    time { () =>
      0 to 50000 foreach { i =>
        val org = new Organization(i.toLong, i.toString)
        orgCache.put(org.getId, org)
      }
    }

    //Get the names of Organization

    val sql = """select p.id, p.name from "orgCache".Organization as p where p.name = ?"""
    val cursor = orgCache.query(new SqlFieldsQuery(sql).setArgs("2"))

    println(s"cursor:${cursor.getAll}")
  }
}
