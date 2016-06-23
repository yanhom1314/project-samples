package com.example

import java.io.File
import java.sql.Connection
import javax.persistence.Persistence

import com.querydsl.codegen.SimpleSerializerConfig
import com.querydsl.jpa.codegen.JPADomainExporter
import scalikejdbc.DBSession

/**
  * Created by YaFengLi on 2016/6/22.
  */
object JPAExportMain extends Service {
  override def call(conn: Connection)(implicit session: DBSession): Unit = {
    val emf = Persistence.createEntityManagerFactory("h2")
    try {
      val export = new JPADomainExporter(new File("target/jpa_gen"), emf.getMetamodel)
      export.execute()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      emf.close()
    }
  }
}
