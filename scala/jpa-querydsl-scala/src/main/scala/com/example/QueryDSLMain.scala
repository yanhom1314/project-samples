package com.example

import java.sql.Connection

import com.querydsl.scala.sql.ScalaMetaDataSerializer
import com.querydsl.scala.{ScalaBeanSerializer, ScalaTypeMappings}
import com.querydsl.sql.codegen.{DefaultNamingStrategy, MetaDataExporter}
import com.querydsl.sql.{H2Templates, SQLQuery}
import scalikejdbc.DBSession

import scala.collection.JavaConversions._

object QueryDSLMain extends Service {

  override def call(conn: Connection)(implicit session: DBSession): Unit = {
    codegen(conn)
    sql(conn)
  }

  def codegen(conn: Connection): Unit = {
    val directory = new java.io.File("target/gen")
    val namingStrategy = new DefaultNamingStrategy()
    val exporter = new MetaDataExporter()
    exporter.setNamePrefix("Q")
    exporter.setPackageName("demo.querydsl")
    exporter.setSchemaPattern("PUBLIC")
    exporter.setTargetFolder(directory)
    exporter.setNamingStrategy(namingStrategy)
    exporter.setSerializerClass(classOf[ScalaMetaDataSerializer])
    exporter.setBeanSerializerClass(classOf[ScalaBeanSerializer])
    exporter.setCreateScalaSources(true)
    exporter.setTypeMappings(ScalaTypeMappings.create)
    println(s"conn:${conn}")
    println(s"metaData:${conn.getMetaData}")
    exporter.export(conn.getMetaData)
  }

  def sql(conn: Connection): Unit = {
    import demo.querydsl.QMembers

    val templates = new H2Templates()
    val query = new SQLQuery(conn, templates)

    val members = QMembers as "m"
    query.select(members).from(members).where(members.id.gt(-1)).fetch().foreach(m => println(s"${m.id} ${m.name} ${m.createdAt}"))
    println("###################")
    query.select(members).from(members).where(members.id.in(List(1, 2))).fetch().foreach(m => println(s"${m.id} ${m.name} ${m.createdAt}"))
    println("###################")
    query.select(members).from(members).where(members.id.in(List(1, 2))).fetch().foreach(m => println(s"${m.id} ${m.name} ${m.createdAt}"))
  }
}
