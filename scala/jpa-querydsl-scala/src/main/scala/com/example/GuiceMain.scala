package com.example

import java.sql.Connection

import com.google.inject.Guice
import guice.{JpaInitializer, ServiceModule}
import repository.PersonRepository
import scalikejdbc.DBSession

import scala.collection.JavaConversions._

/**
  * Created by YaFengLi on 2016/6/23.
  */
object GuiceMain extends Service {
  override def call(conn: Connection)(implicit session: DBSession): Unit = {
    val injector = Guice.createInjector(new ServiceModule())
    val jpaInitializer = injector.getInstance(classOf[JpaInitializer])
    val personRepository = injector.getInstance(classOf[PersonRepository])
    try {
      println("personRepository:${personRepository.toString}")
      println("size:" + personRepository.all.size())
      personRepository.all.foreach(p => println(s"${p.id} ${p.name} ${p.age}"))
    }
    catch {
      case e: Exception => throw e
    }
    finally {
      Thread.sleep(1000)
      println("###############Exit 0#############")
      jpaInitializer.service.stop()
    }
  }
}
