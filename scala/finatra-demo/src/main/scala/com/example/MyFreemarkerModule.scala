package com.example

import com.example.freemarker.FreemarkerFactory
import com.google.inject.Provides
import com.twitter.finatra.http.routing.FileResolver
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag

/**
  * Created by LYF on 2016/10/23.
  */
object MyFreemarkerModule extends TwitterModule {


  @Provides
  @Singleton
  def provideFreemarkerConfig(resolver: FileResolver,
                              @Flag("local.doc.root") localDocRoot: String): FreemarkerFactory = {

    val factory = new FreemarkerFactory()
    factory
  }
}

