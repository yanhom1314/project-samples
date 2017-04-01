package com.example

import com.example.jdbi.dao.{SomethingRepository, UserRepository}
import com.twitter.inject.{Injector, TwitterModule}
import org.h2.jdbcx.JdbcConnectionPool
import org.h2.tools.Server
import org.skife.jdbi.v2.tweak.HandleCallback
import org.skife.jdbi.v2.{DBI, Handle}


object H2Module extends TwitterModule {
  //val server = Server.createTcpServer()
  lazy val ds = JdbcConnectionPool.create("jdbc:h2:mem:test", "sa", "")
  lazy val dbi = new DBI(ds)

  protected override def configure(): Unit = {
    dbi.withHandle((handle: Handle) => {
      val sr = handle.attach(classOf[SomethingRepository])
      val ur = handle.attach(classOf[UserRepository])
      //jdbi create table
      sr.createTable()
      ur.createTable()
    })

    bind[DBI].toInstance(dbi)
  }

  override def singletonStartup(injector: Injector): Unit = {
    //server.start()
  }

  override def singletonShutdown(injector: Injector): Unit = {
    //server.shutdown()
  }
}
