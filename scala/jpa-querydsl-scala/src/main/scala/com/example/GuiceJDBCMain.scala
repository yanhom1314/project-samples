package com.example

import java.sql.Connection

import com.google.inject.Guice
import guice.{ConnectionContext, JDBCServiceModule}
import repository.MembersRepository
import scalikejdbc.DBSession

/**
  * Created by YaFengLi on 2016/6/23.
  */
object GuiceJDBCMain extends Service {
  override def call(conn: Connection)(implicit session: DBSession): Unit = {
    val injector = Guice.createInjector(new JDBCServiceModule())

    val ctx = injector.getInstance(classOf[ConnectionContext])

    val membersRepository = injector.getInstance(classOf[MembersRepository])
    println(s"membersRepository:${membersRepository}")
    println(membersRepository)
    println(membersRepository)
    println(membersRepository.connectionContext)
    println(membersRepository.all())
    membersRepository.all().foreach(t => println(s"${t.id} ${t.name} ${t.createdAt}"))

    ctx.dataSource.close()
  }
}
