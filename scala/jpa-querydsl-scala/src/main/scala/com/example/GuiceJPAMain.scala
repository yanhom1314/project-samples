package com.example

import java.sql.Connection

import com.google.inject.Guice
import guice.{JpaInitializer, JPAServiceModule}
import repository.PersonRepository
import scalikejdbc.DBSession

import scala.collection.JavaConversions._

/**
  * Created by YaFengLi on 2016/6/23.
  */
object GuiceJPAMain extends Service {
  override def call(conn: Connection)(implicit session: DBSession): Unit = {
    val injector = Guice.createInjector(new JPAServiceModule())
    val jpaInitializer = injector.getInstance(classOf[JpaInitializer])
    val personRepository = injector.getInstance(classOf[PersonRepository])
    try {
      println(s"personRepository:${personRepository.toString} class:${personRepository.c.getCanonicalName}")
      println("size:" + personRepository.all.size())
      personRepository.all.foreach(p => println(s"${p.id} ${p.name} ${p.age}"))

      println(personRepository.findById(1L))
    }
    catch {
      case e: Exception => throw e
    }
    finally {
      jpaInitializer.service.stop()
    }
  }
}
