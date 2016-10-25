package com.example

import com.example.freemarker._
import com.twitter.finatra.http.internal.marshalling.MessageBodyManager
import com.twitter.finatra.http.internal.marshalling.mustache.MustacheMessageBodyWriter
import com.twitter.finatra.http.marshalling.mustache.MustacheBodyComponent
import com.twitter.finatra.http.marshalling.{DefaultMessageBodyReader, DefaultMessageBodyWriter}
import com.twitter.finatra.response.Mustache
import com.twitter.inject.{Injector, InjectorModule, TwitterModule}

/**
  * Created by YaFengLi on 2016/10/25.
  */
object MyMessageBodyModule extends TwitterModule {

  flag("http.response.charset.enabled", true, "Return HTTP Response Content-Type UTF-8 Charset")

  override val modules = Seq(InjectorModule)

  override def configure() {
    bindSingleton[DefaultMessageBodyReader].to[ExampleDefaultMessageBodyReader]
    bindSingleton[DefaultMessageBodyWriter].to[ExampleDefaultMessageBodyWriter]
  }

  override def singletonStartup(injector: Injector) {
    println("Configuring--2 FreemarkerMessageManager")
    val manager = injector.instance[MessageBodyManager]
    manager.addByAnnotation[Freemarker, FreemarkerMessageBodyWriter]()
    manager.addByComponentType[FreemarkerBodyComponent, FreemarkerMessageBodyWriter]()

    manager.addByAnnotation[Mustache, MustacheMessageBodyWriter]()
    manager.addByComponentType[MustacheBodyComponent, MustacheMessageBodyWriter]()
  }
}
