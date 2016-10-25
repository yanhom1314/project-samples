package com.example

import java.io.File
import javax.inject.Singleton

import com.example.freemarker.{Freemarker, FreemarkerBodyComponent, FreemarkerFactory, FreemarkerMessageBodyWriter}
import com.google.inject.Provides
import com.twitter.finatra.http.internal.marshalling.MessageBodyManager
import com.twitter.finatra.http.modules.DocRootModule
import com.twitter.finatra.http.routing.FileResolver
import com.twitter.inject.annotations.Flag
import com.twitter.inject.{Injector, TwitterModule}

object FreemarkerModule extends TwitterModule {

  override def modules = Seq(DocRootModule)

  @Provides
  @Singleton
  def provideFreemarkerFactory(resolver: FileResolver, @Flag("local.doc.root") localDocRoot: String): FreemarkerFactory = {

    val factory = new FreemarkerFactory()

    new File(localDocRoot) match {
      case f: File if f != null && f.isDirectory => factory.configuration.setDirectoryForTemplateLoading(new File(localDocRoot))
      case _ => factory.configuration.setClassLoaderForTemplateLoading(this.getClass.getClassLoader, "templates")
    }

    println("factory:" + factory)
    factory
  }

  override def singletonStartup(injector: Injector) {
    println("Configuring--1 FreemarkerMessageManager")
    val manager = injector.instance[MessageBodyManager]
    manager.addByAnnotation[Freemarker, FreemarkerMessageBodyWriter]()
    manager.addByComponentType[FreemarkerBodyComponent, FreemarkerMessageBodyWriter]()
  }
}
