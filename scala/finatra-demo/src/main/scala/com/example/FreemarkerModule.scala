package com.example

import java.io.File
import javax.inject.Singleton

import com.example.freemarker.FreemarkerFactory
import com.google.inject.Provides
import com.twitter.finatra.http.routing.FileResolver
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag

object FreemarkerModule extends TwitterModule {

  @Provides
  @Singleton
  def provideFreemarkerFactory(resolver: FileResolver,
                               @Flag("local.doc.root") localDocRoot: String): FreemarkerFactory = {

    val factory = new FreemarkerFactory()

    new File(localDocRoot) match {
      case f: File if f != null && f.isDirectory => factory.configuration.setDirectoryForTemplateLoading(new File(localDocRoot))
      case _ => factory.configuration.setClassLoaderForTemplateLoading(this.getClass.getClassLoader, "templates")
    }

    factory
  }
}
