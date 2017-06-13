package com.example

import java.io.File
import java.net.JarURLConnection

import com.example.http._
import com.google.inject.Module
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.http.{Controller, HttpServer}
import finatra.freemarker.FreemarkerModule
import finatra.greatbit.module.{BeetlModule, ShiroModule, SpringDataJpaModule}

import scala.collection.JavaConverters._

object ExampleServerMain extends ExampleServer

class ExampleServer extends HttpServer {
  override def defaultFinatraHttpPort = ":80"

  override protected def modules: Seq[Module] = Array(FreemarkerModule, BeetlModule, H2Module, SpringDataJpaModule, ShiroModule)

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[PingController]
      .add[HomeController]
      .add[UserController]
      .add[ViewController]
      .add[DbController]
      .add[SecurityController]

    scan("com.example.http", router)
  }

  private def scan(packageName: String, router: HttpRouter): Unit = {
    val packageDirName = packageName.replace(".", "/")

    val url = Thread.currentThread().getContextClassLoader.getResource(packageDirName)

    url.getProtocol match {
      case "file" => new File(url.getFile).listFiles().map(f => Class.forName(f.getName.replace("$.class", "").replace(".class", "").replace("/", "."))).toIterator
      case "jar" =>
        val jar = url.openConnection().asInstanceOf[JarURLConnection].getJarFile

        jar.entries().asScala.filter(j => j.getName.startsWith(packageDirName) && j.getName.endsWith(".class"))
          .map(j => {
            println(j.getName.replace("$.class", "").replace(".class", "").replace("/", "."))
            Class.forName(j.getName.replace("$.class", "").replace(".class", "").replace("/", "."))
          })
          .filter(c => c.isAssignableFrom(classOf[Controller]))
          .foreach(c => {
            println(c.getName)
            router.add(c.asInstanceOf[Controller])
          })
    }
  }
}
