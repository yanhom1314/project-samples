package com.example

import io.undertow.servlet.Servlets
import io.undertow.{Handlers, Undertow}


/**
  * Created by YaFengLi on 2016/10/9.
  */
object WarRunner extends App {
  val servletBuilder = Servlets.deployment()
    .setClassLoader(this.getClass.getClassLoader)
    .setContextPath("/myapp")
    .setDeploymentName("e:/tmp/dns114admin.war")
    //.addListener(Servlets.listener(classOf[]))

  val manager = Servlets.defaultContainer().addDeployment(servletBuilder)
  manager.deploy()

  val path = Handlers.path(Handlers.redirect("/myapp")).addPrefixPath("/myapp", manager.start())
  val server = Undertow.builder()
    .addHttpListener(8080, "localhost")
    .setHandler(path).build()
  //.setHandler(manager.start()).build()
  server.start()
}
